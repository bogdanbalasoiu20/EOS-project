package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.request.Auth.LoginRequest;
import com.example.demo.tasks.dto.request.User.CreateUserRequest;
import com.example.demo.tasks.dto.response.Auth.LoginResponse;
import com.example.demo.tasks.dto.response.User.UserResponse;
import com.example.demo.tasks.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws JoseException {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
