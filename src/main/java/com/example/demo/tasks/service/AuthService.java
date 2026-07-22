package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.UserMapper;
import com.example.demo.tasks.dto.request.Auth.LoginRequest;
import com.example.demo.tasks.dto.request.User.CreateUserRequest;
import com.example.demo.tasks.dto.response.Auth.LoginResponse;
import com.example.demo.tasks.dto.response.User.UserResponse;
import com.example.demo.tasks.exception.UnauthorizedException;
import com.example.demo.tasks.exception.UserAlreadyExistsException;
import com.example.demo.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.security.Credential;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.secret: }") String jwtSecret;
    @Value("${jwt.expiration.ms: }") String jwtExpiration;

    public LoginResponse login(LoginRequest credentials) throws JoseException {
        String email = new String(Base64.getDecoder().decode(credentials.email()));
        String password = new String(Base64.getDecoder().decode(credentials.password()));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        String hashPassword = Credential.MD5
                .digest(password)
                .replaceFirst("MD5:", "")
                .trim()
                .toLowerCase();

        if (!hashPassword.equals(user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = createJwtToken(credentials.email());

        return new LoginResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                token,
                "Login successful"
        );
    }

    public UserResponse register(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.username())){
            throw new UserAlreadyExistsException("username", request.username());
        }

        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("email",request.email());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(Credential.MD5.digest(request.password())
                .replaceFirst("MD5:", "")
                .trim()
                .toLowerCase());

        return userMapper.toResponse(userRepository.save(user));

    }

    private String createJwtToken(String email) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float) Long.parseLong(jwtExpiration)/(1000*60));
        claims.setSubject(email);
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return jws.getCompactSerialization();
    }

}
