package com.example.demo.tasks.dto.request;

import com.example.demo.tasks.domain.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
        @Size(max=200, message="Content cannot exceed 200 characters")
        String content,

        @Future(message="Due date must be in the future")
        LocalDateTime dueDate,

        TaskStatus status

) {}
