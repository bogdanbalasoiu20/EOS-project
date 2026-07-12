package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.Task;
import com.example.demo.tasks.dto.request.Task.CreateTaskRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toEntity(CreateTaskRequest request) {
        return Task.builder()
                .taskName(request.taskName())
                .dueDate(request.dueDate())
                .createdBy(request.createdBy())
                .lastUpdatedBy(request.createdBy())
                .build();
    }

    public TaskResponse toResponse(Task task) {
        Long userId = null;
        String username = null;

        if (task.getUser() != null) {
            userId = task.getUser().getUserId();
            username = task.getUser().getUsername();
        }

        return new TaskResponse(
                task.getTaskId(),
                task.getTaskName(),
                task.getStatusType().getStatusTypeId(),
                task.getStatusType().getStatusName(),
                userId,
                username,
                task.getDueDate(),
                task.getCreatedBy(),
                task.getCreationDate(),
                task.getLastUpdatedBy(),
                task.getLastUpdateDate(),
                task.getCreatedByFullname()
        );
    }

}
