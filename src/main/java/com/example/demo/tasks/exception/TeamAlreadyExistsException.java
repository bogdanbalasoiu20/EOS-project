package com.example.demo.tasks.exception;

public class TeamAlreadyExistsException extends RuntimeException {
    public TeamAlreadyExistsException(String teamName) {
        super("Team with name '" + teamName + "' already exists.");
    }
}