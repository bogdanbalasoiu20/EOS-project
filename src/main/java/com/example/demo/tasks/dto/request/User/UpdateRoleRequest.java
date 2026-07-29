package com.example.demo.tasks.dto.request.User;

import com.example.demo.tasks.domain.enums.RoleName;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(
        @NotNull
        RoleName role
) {}
