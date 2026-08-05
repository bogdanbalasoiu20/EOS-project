package com.example.demo.tasks.dto.request.TaskComment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskCommentRequest(
        @NotBlank
        @Size(max = 1000)
        String content,

        Long parentCommentId
) {}
