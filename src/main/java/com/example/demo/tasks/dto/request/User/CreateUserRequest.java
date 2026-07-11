package com.example.demo.tasks.dto.request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank(message = "Username is required")
        String username,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @NotNull(message = "Internal flag is required")
        Integer internal,

        @NotBlank(message = "Created by is required")
        String createdBy

) {}
