package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.request.TaskComment.CreateTaskCommentRequest;
import com.example.demo.tasks.dto.request.TaskComment.UpdateTaskCommentRequest;
import com.example.demo.tasks.dto.response.TaskComment.TaskCommentResponse;
import com.example.demo.tasks.service.TaskCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class TaskCommentController {
    private final TaskCommentService taskCommentService;

    @GetMapping
    public ResponseEntity<List<TaskCommentResponse>> getComments(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskCommentService.getComments(taskId));
    }


    @PostMapping
    public ResponseEntity<TaskCommentResponse> createComment(@PathVariable Long taskId, @Valid @RequestBody CreateTaskCommentRequest request) {
        TaskCommentResponse response = taskCommentService.createComment(taskId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/{commentId}")
    public ResponseEntity<TaskCommentResponse> updateComment(@PathVariable Long taskId, @PathVariable Long commentId, @Valid @RequestBody UpdateTaskCommentRequest request) {
        return ResponseEntity.ok(taskCommentService.updateComment(taskId, commentId, request)
        );
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long taskId, @PathVariable Long commentId) {
        taskCommentService.deleteComment(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
