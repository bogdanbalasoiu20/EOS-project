package com.example.demo.tasks.dto.response.Auth;

import com.example.demo.tasks.domain.enums.RoleName;
import com.example.demo.tasks.domain.model.Role;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String token,
        String role,
        String message
) {}
