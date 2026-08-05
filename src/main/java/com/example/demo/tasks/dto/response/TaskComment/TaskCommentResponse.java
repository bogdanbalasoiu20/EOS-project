package com.example.demo.tasks.dto.response.TaskComment;

import java.time.LocalDateTime;
import java.util.List;

public record TaskCommentResponse(
        Long commentId,
        String content,
        Long userId,
        String username,
        LocalDateTime creationDate,
        LocalDateTime lastUpdateDate,
        boolean deleted,
        List<TaskCommentResponse> replies
) {}
