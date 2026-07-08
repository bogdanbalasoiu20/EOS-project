package com.example.demo.tasks.exception;

public class TaskAlreadyCompletedException extends RuntimeException {

    public TaskAlreadyCompletedException(Long id) {
        super("Task with id " + id + " is already completed.");
    }

}
