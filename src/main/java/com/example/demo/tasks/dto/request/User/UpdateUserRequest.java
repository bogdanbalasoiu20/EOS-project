package com.example.demo.tasks.dto.request.User;

import java.time.LocalDate;

public record UpdateUserRequest(
        String username,
        LocalDate birthDate,
        Integer internal
) {}
