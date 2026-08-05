package com.example.demo.tasks.dto.request.TaskComment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskCommentRequest(
        @NotBlank
        @Size(max = 1000)
        String content
) {}
