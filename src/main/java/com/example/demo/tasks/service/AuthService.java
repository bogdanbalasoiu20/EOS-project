package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.request.Auth.LoginRequest;
import com.example.demo.tasks.dto.response.Auth.LoginResponse;
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

    @Value("${jwt.secret: }") String jwtSecret;
    @Value("${jwt.expiration.ms: }") String jwtExpiration;

    public LoginResponse login(LoginRequest credentials) throws JoseException {
        String email = new String(Base64.getDecoder().decode(credentials.email()));
        String password = new String(Base64.getDecoder().decode(credentials.password()));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        String hashPassword = Credential.MD5
                .digest(password)
                .replaceFirst("MD5:", "")
                .trim()
                .toLowerCase();

        if (!hashPassword.equals(user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
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


//    public ResponseEntity<LoginResponse> login(LoginRequest request) {
//        Optional<User> optionalUser = userRepository.findByEmail(request.email());
//
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null,null,null,null, "Invalid email or password"));
//        }
//
//        User user = optionalUser.get();
//
//        if (!user.getPassword().equals(request.password())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null,null,null,"Invalid email or password"));
//        }
//
//        return ResponseEntity.ok(new LoginResponse(user.getUserId(),user.getUsername(), user.getEmail(), user.getPassword(), "Login successful"));
//    }
}
