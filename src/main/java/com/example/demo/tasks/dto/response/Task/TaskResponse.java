package com.example.demo.tasks.dto.response.Task;


import java.time.LocalDateTime;

public record TaskResponse(
        Long taskId,
        String taskName,
        String statusTypeId,
        String statusName,
        Long userId,
        String username,
        Long teamId,
        String teamName,
        LocalDateTime dueDate,
        String createdBy,
        LocalDateTime creationDate,
        String lastUpdatedBy,
        LocalDateTime lastUpdateDate,
        String createdByFullname
) {
}
