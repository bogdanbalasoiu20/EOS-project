package com.example.demo.tasks.dto.request;

import com.example.demo.tasks.domain.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotNull(message="Id is required")
        Long id,

        @NotBlank(message="Content is required")
        @Size(max=200, message="Content cannot exceed 200 characters")
        String content,

        @NotNull(message="Due date is required")
        @Future(message="Due date must be in the future")
        LocalDateTime dueDate,

        @NotNull(message="Status is required")
        TaskStatus status

) {}
