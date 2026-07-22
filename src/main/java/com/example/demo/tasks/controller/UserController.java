package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.request.User.CreateUserRequest;
import com.example.demo.tasks.dto.request.User.UpdateUserRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import com.example.demo.tasks.dto.response.User.UserResponse;
import com.example.demo.tasks.dto.response.User.UserTaskCountResponse;
import com.example.demo.tasks.service.TaskService;
import com.example.demo.tasks.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    //testat, merge
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false) String username, @RequestParam(required = false) Integer internal) {
        return ResponseEntity.ok(userService.getUsers(username, internal));
    }

    //testat, merge
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    //testat, merge
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    //testat. merge
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //testat, merge
    @GetMapping("/without-tasks")
    public ResponseEntity<List<UserResponse>> getUsersWithoutTasks() {
        return ResponseEntity.ok(userService.getUsersWithoutTasks());
    }

    //testat, merge
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskResponse>> getUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserTasks(userId));
    }

    //testat, merge
    @GetMapping("/{userId}/task-count")
    public ResponseEntity<UserTaskCountResponse> getTaskCount(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getTaskCount(userId));
    }

    //testat, merge
    @GetMapping("/{userId}/tasks/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getOverdueTasks(userId));
    }

    //testat, merge
    @GetMapping("/{userId}/tasks/today")
    public ResponseEntity<List<TaskResponse>> getTodayTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTodayTasks(userId));
    }

    //testat, merge
    @GetMapping("/{userId}/tasks/this-week")
    public ResponseEntity<List<TaskResponse>> getThisWeekTasks(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getThisWeekTasks(userId));
    }

    //testat, merge
    @GetMapping("/{userId}/tasks/date-range")
    public ResponseEntity<List<TaskResponse>> getTasksBetween(@PathVariable Long userId, @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(taskService.getTasksBetween(userId, start, end));
    }
}