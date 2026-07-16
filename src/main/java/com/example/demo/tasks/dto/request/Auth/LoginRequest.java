package com.example.demo.tasks.dto.request.Auth;

public record LoginRequest(
        String email,
        String password
) {}
