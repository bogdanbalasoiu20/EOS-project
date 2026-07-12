package com.example.demo.tasks.dto.request.Task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank(message = "Task name is required")
        String taskName,

        @NotNull(message = "Status type is required")
        String statusTypeId,

        //@NotNull(message = "User is required")
        Long userId,

        @NotNull(message = "Due date is required")
        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate,

        @NotBlank(message = "Created by is required")
        String createdBy

) {}
