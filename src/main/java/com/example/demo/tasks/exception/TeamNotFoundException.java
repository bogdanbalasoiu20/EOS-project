package com.example.demo.tasks.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long teamId) {
        super("Team with id " + teamId + " not found.");
    }
}