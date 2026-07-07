package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.Task;
import com.example.demo.tasks.dto.mapper.TaskMapper;
import com.example.demo.tasks.dto.request.CreateTaskRequest;
import com.example.demo.tasks.dto.request.UpdateTaskRequest;
import com.example.demo.tasks.dto.response.TaskResponse;
import com.example.demo.tasks.exception.TaskAlreadyExistsException;
import com.example.demo.tasks.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public List<TaskResponse> getTasks() {
        log.info("Getting tasks ");
        return tasks.stream().map(TaskMapper::toResponse).toList();
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        validateUniqueId(request.id());
        Task task = TaskMapper.toModel(request);

        tasks.add(task);
        log.info("Added task: {}", task);

        return TaskMapper.toResponse(task);
    }


    public void deleteTask(Long taskId) {
        Task task = findTask(taskId);
        log.info("Deleting task with id {}", taskId);
        tasks.remove(task);
    }

    public TaskResponse updateTask(Long taskId, UpdateTaskRequest updatedTask) {
        Task task = findTask(taskId);

        if(updatedTask.content()!=null){
            task.setContent(updatedTask.content());
        }

        if(updatedTask.dueDate()!=null){
            task.setDueDate(updatedTask.dueDate());
        }

        if(updatedTask.status()!=null){
            task.setStatus(updatedTask.status());
        }
        log.info("Updating task with id {}", taskId);

        return TaskMapper.toResponse(task);
    }

    public TaskResponse getTaskById(Long taskId) {
        log.info("Getting task with id {}", taskId);
        return TaskMapper.toResponse(findTask(taskId));
    }

    private void validateUniqueId(Long id){
        boolean exists = tasks.stream().anyMatch(task -> task.getId().equals(id));

        if(exists){
            throw new TaskAlreadyExistsException(id);
        }
    }

    private Task findTask(Long id){
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(() -> new TaskNotFoundException(id));
    }

}
