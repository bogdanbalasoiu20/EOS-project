package com.example.demo.tasks.dto.response.Task;

import com.example.demo.tasks.domain.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long taskId,
        String taskName,
        String statusTypeId,
        String statusName,
        Long userId,
        String username,
        LocalDateTime dueDate,
        String createdBy,
        LocalDateTime creationDate,
        String lastUpdatedBy,
        LocalDateTime lastUpdateDate,
        String createdByFullname
) {}
