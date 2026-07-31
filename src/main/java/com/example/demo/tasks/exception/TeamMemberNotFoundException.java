package com.example.demo.tasks.exception;

public class TeamMemberNotFoundException extends RuntimeException {
    public TeamMemberNotFoundException(Long teamId, Long userId) {
        super("User " + userId + " is not a member of team " + teamId + ".");
    }
}