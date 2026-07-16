package com.example.demo.tasks.dto.response.Auth;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String password,
        String message
) {
}
