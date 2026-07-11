package com.example.demo.tasks.exception;


public class StatusTypeAlreadyExistsException extends RuntimeException {
    public StatusTypeAlreadyExistsException(String statusName) {
        super("Status type '" + statusName + "' already exists.");
    }
}
