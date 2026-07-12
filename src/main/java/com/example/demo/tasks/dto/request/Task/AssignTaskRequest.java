package com.example.demo.tasks.dto.request.Task;

import jakarta.validation.constraints.NotNull;

public record AssignTaskRequest(
        @NotNull(message = "User id is required")
        Long userId
) {}
