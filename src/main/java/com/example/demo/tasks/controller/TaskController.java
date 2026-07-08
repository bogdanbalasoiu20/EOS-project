package com.example.demo.tasks.controller;

import com.example.demo.tasks.domain.TaskStatus;
import com.example.demo.tasks.dto.request.CreateTaskRequest;
import com.example.demo.tasks.dto.request.UpdateTaskRequest;
import com.example.demo.tasks.dto.response.TaskResponse;
import com.example.demo.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId,@Valid @RequestBody UpdateTaskRequest request){
        return ResponseEntity.ok(taskService.updateTask(taskId,request));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam(required = false) TaskStatus status, @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(taskService.getTasks(status, keyword));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }

    @GetMapping("/today")
    public ResponseEntity<List<TaskResponse>> getTodayTasks() {
        return ResponseEntity.ok(taskService.getTodayTasks());
    }

    @GetMapping("/this-week")
    public ResponseEntity<List<TaskResponse>> getThisWeekTasks() {
        return ResponseEntity.ok(taskService.getThisWeekTasks());
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

}
