package com.example.demo.tasks.controller;

import com.example.demo.tasks.domain.enums.TaskPeriod;
import com.example.demo.tasks.dto.request.Task.AssignTaskRequest;
import com.example.demo.tasks.dto.request.Task.CreateTaskRequest;
import com.example.demo.tasks.dto.request.Task.UpdateTaskRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import com.example.demo.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    //testat, merge
    @GetMapping
    @PreAuthorize("@checkPermissions.hasPermission('TASK','READ')")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Boolean unassigned,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(required = false) TaskPeriod period,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return ResponseEntity.ok(taskService.getTasks(status, keyword, userId, unassigned, dueDate, period, start, end));
    }

    //testat, merge
    @GetMapping("/{taskId}")
    @PreAuthorize("@checkPermissions.hasPermission('TASK','READ')")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    //testat, merge
    @PostMapping
    @PreAuthorize("@checkPermissions.hasPermission('TASK','CREATE')")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    //testat, merge
    @PatchMapping("/{taskId}")
    @PreAuthorize("@checkPermissions.hasPermission('TASK','UPDATE')")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    //testat, merge
    @DeleteMapping("/{taskId}")
    @PreAuthorize("@checkPermissions.hasPermission('TASK','DELETE')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    //testat, merge
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    //testat, merge
    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<TaskResponse> assignTask(@PathVariable Long taskId, @Valid @RequestBody AssignTaskRequest request) {
        return ResponseEntity.ok(taskService.assignTask(taskId, request));
    }

    //testat, merge
    @GetMapping("/unassigned")
    public ResponseEntity<List<TaskResponse>> getUnassignedTasks() {
        return ResponseEntity.ok(taskService.getUnassignedTasks());
    }

}
