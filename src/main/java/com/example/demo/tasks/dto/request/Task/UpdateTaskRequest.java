package com.example.demo.tasks.dto.request.Task;

import com.example.demo.tasks.domain.TaskStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
        String taskName,
        String statusTypeId,
        Long userId,

        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate

) {}
