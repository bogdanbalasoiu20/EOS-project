package com.example.demo.tasks.exception;

public class UserNotInTeamException extends RuntimeException{
    public UserNotInTeamException(Long userId, Long teamId) {
        super("User " + userId + " is not in team " + teamId);
    }
}
