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
        //decodez datele din request din base64 in text clar
        String email = new String(Base64.getDecoder().decode(credentials.email()));
        String password = new String(Base64.getDecoder().decode(credentials.password()));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        //hash cu algoritmul MD5
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
        String username = new String(Base64.getDecoder().decode(request.username()));
        String email = new String(Base64.getDecoder().decode(request.email()));
        String password = new String(Base64.getDecoder().decode(request.password()));

        if(userRepository.existsByUsername(request.username())){
            throw new UserAlreadyExistsException("username", request.username());
        }

        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("email",request.email());
        }

        User user = userMapper.toEntity(request);

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(Credential.MD5.digest(password)
                .replaceFirst("MD5:", "")
                .trim()
                .toLowerCase());

        return userMapper.toResponse(userRepository.save(user));

    }

    private String createJwtToken(String email) throws JoseException {
        JwtClaims claims = new JwtClaims();  //creez continutul tokenului
        claims.setIssuedAtToNow();  //setez data emiterii (iat - issued at)
        claims.setExpirationTimeMinutesInTheFuture((float) Long.parseLong(jwtExpiration)/(1000*60));  //setez data expirarii (tokenul e valid o ora)
        claims.setSubject(email);  //setez utilizatorul prin emailul sau
        JsonWebSignature jws = new JsonWebSignature();  //creez tokenul
        jws.setPayload(claims.toJson());  //pun informatia in token
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256); //aleg algortimul de semnare (pt a stii daca tokenul sufera modificari)
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8))); //setez cheia secreta (pentru semnare tokenului)
        return jws.getCompactSerialization();  //generez jwt-ul
    }

}
