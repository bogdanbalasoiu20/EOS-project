package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.TaskStatus;
import com.example.demo.tasks.domain.model.Task;
import com.example.demo.tasks.dto.mapper.TaskMapper;
import com.example.demo.tasks.dto.request.Task.CreateTaskRequest;
import com.example.demo.tasks.dto.request.Task.UpdateTaskRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import com.example.demo.tasks.exception.TaskAlreadyCompletedException;
import com.example.demo.tasks.exception.TaskAlreadyExistsException;
import com.example.demo.tasks.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskService {
//    private final List<Task> tasks = new ArrayList<>();
//
//    public List<TaskResponse> getTasks(TaskStatus status, String keyword) {
//        return tasks.stream()
//                .filter(task -> status == null || task.getStatus() == status)
//                .filter(task -> keyword == null || task.getContent().toLowerCase().contains(keyword.toLowerCase()))
//                .map(TaskMapper::toResponse)
//                .toList();
//    }
//
//    public TaskResponse createTask(CreateTaskRequest request) {
//        validateUniqueId(request.id());
//        Task task = TaskMapper.toModel(request);
//
//        tasks.add(task);
//        log.info("Added task: {}", task);
//
//        return TaskMapper.toResponse(task);
//    }
//
//
//    public void deleteTask(Long taskId) {
//        Task task = findTask(taskId);
//        log.info("Deleting task with id {}", taskId);
//        tasks.remove(task);
//    }
//
//    public TaskResponse updateTask(Long taskId, UpdateTaskRequest updatedTask) {
//        Task task = findTask(taskId);
//
//        if(updatedTask.content()!=null){
//            task.setContent(updatedTask.content());
//        }
//
//        if(updatedTask.dueDate()!=null){
//            task.setDueDate(updatedTask.dueDate());
//        }
//
//        if(updatedTask.status()!=null){
//            task.setStatus(updatedTask.status());
//        }
//        log.info("Updating task with id {}", taskId);
//
//        return TaskMapper.toResponse(task);
//    }
//
//    public TaskResponse getTaskById(Long taskId) {
//        log.info("Getting task with id {}", taskId);
//        return TaskMapper.toResponse(findTask(taskId));
//    }
//
//    private void validateUniqueId(Long id){
//        boolean exists = tasks.stream().anyMatch(task -> task.getId().equals(id));
//
//        if(exists){
//            throw new TaskAlreadyExistsException(id);
//        }
//    }
//
//    private Task findTask(Long id){
//        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(() -> new TaskNotFoundException(id));
//    }
//
//    public List<TaskResponse> getOverdueTasks() {
//        log.info("Getting overdue tasks");
//
//        return tasks.stream()
//                .filter(task -> task.getDueDate().isBefore(LocalDateTime.now()) && task.getStatus() != TaskStatus.DONE)
//                .map(TaskMapper::toResponse)
//                .toList();
//    }
//
//    private List<TaskResponse> getTasksBetween(LocalDate start, LocalDate end) {
//        return tasks.stream()
//                .filter(task -> {
//                    LocalDate dueDate = task.getDueDate().toLocalDate();
//
//                    return !dueDate.isBefore(start) && !dueDate.isAfter(end);
//                })
//                .map(TaskMapper::toResponse)
//                .toList();
//    }
//
//    public List<TaskResponse> getTodayTasks() {
//        LocalDate today = LocalDate.now();
//
//        return getTasksBetween(today, today);
//    }
//
//    public List<TaskResponse> getThisWeekTasks() {
//        LocalDate today = LocalDate.now();
//
//        return getTasksBetween(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY));
//    }
//
//    public TaskResponse completeTask(Long taskId) {
//        log.info("Completing task with id {}", taskId);
//        Task task = findTask(taskId);
//
//        if (task.getStatus() == TaskStatus.DONE) {
//            throw new TaskAlreadyCompletedException(taskId);
//        }
//
//        task.setStatus(TaskStatus.DONE);
//        return TaskMapper.toResponse(task);
//    }

}
