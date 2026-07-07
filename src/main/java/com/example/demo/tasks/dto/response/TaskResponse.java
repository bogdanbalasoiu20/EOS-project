package com.example.demo.tasks.dto.response;

import com.example.demo.tasks.domain.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String content,
        LocalDateTime dueDate,
        TaskStatus status
) {}
