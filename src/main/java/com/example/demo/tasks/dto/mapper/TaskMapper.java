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
        return new TaskResponse(
                task.getTaskId(),
                task.getTaskName(),
                task.getStatusType().getStatusTypeId(),
                task.getStatusType().getStatusName(),
                task.getUser().getUserId(),
                task.getUser().getUsername(),
                task.getDueDate(),
                task.getCreatedBy(),
                task.getCreationDate(),
                task.getLastUpdatedBy(),
                task.getLastUpdateDate(),
                task.getCreatedByFullname()
        );
    }

}
