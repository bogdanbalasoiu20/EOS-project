package com.example.demo.tasks.exception;

public class StatusTypeNotFoundException extends RuntimeException {
    public StatusTypeNotFoundException(String id) {
        super("Status type with id '" + id + "' was not found.");
    }
}
