package com.example.demo.tasks.dto.response.User;

public record UserTaskCountResponse(
        Long userId,
        long taskCount
) {}