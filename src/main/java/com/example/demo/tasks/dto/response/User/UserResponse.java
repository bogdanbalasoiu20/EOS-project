package com.example.demo.tasks.dto.response.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String username,
        LocalDate birthDate,
        Boolean internal,
        String createdBy,
        LocalDateTime creationDate,
        String lastUpdatedBy,
        LocalDateTime lastUpdateDate,
        String createdByFullname

) {}
