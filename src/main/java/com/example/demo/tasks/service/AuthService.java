package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.request.Auth.LoginRequest;
import com.example.demo.tasks.dto.response.Auth.LoginResponse;
import com.example.demo.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null,null,null,null, "Invalid email or password"));
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null,null,null,"Invalid email or password"));
        }

        return ResponseEntity.ok(new LoginResponse(user.getUserId(),user.getUsername(), user.getEmail(), user.getPassword(), "Login successful"));
    }
}
