package com.example.demo.tasks.exception;

public class TaskAlreadyAssignedException extends RuntimeException {
    public TaskAlreadyAssignedException(Long taskId, Long userId) {
        super("Task " + taskId + " is already assigned to user " + userId + ".");
    }
}
