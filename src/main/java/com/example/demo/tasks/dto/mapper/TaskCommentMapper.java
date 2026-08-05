package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.TaskComment;
import com.example.demo.tasks.dto.request.TaskComment.CreateTaskCommentRequest;
import com.example.demo.tasks.dto.response.TaskComment.TaskCommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskCommentMapper {

    public TaskComment toEntity(CreateTaskCommentRequest request) {
        TaskComment comment = new TaskComment();

        comment.setContent(request.content());

        return comment;
    }

    public TaskCommentResponse toResponse(TaskComment comment) {
        return toResponse(comment, List.of());
    }

    public TaskCommentResponse toResponse(TaskComment comment, List<TaskCommentResponse> replies) {
        return new TaskCommentResponse(
                comment.getCommentId(),
                comment.isDeleted() ? null : comment.getContent(),
                comment.getUser().getUserId(),
                comment.getUser().getUsername(),
                comment.getCreationDate(),
                comment.getLastUpdateDate(),
                comment.isDeleted(),
                replies
        );
    }
}
