package com.example.demo.tasks.exception;

public class TeamMemberAlreadyExistsException extends RuntimeException {
    public TeamMemberAlreadyExistsException(Long teamId, Long userId) {
        super("User " + userId + " is already a member of team " + teamId + ".");
    }
}