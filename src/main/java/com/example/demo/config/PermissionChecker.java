package com.example.demo.config;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.service.TaskService;
import com.example.demo.tasks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component("checkPermissions")
public class PermissionChecker {
    private final UserService userService;
    private TaskService taskService;

    public PermissionChecker(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    public boolean hasPermission(String resource, String action) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                log.info("Authentication is null");
                return false;
            }

            String email = authentication.getName();
            String emailDecoded = new String(Base64.getDecoder().decode(email));
            log.info("Decoded email: "+ emailDecoded);
            log.info("Checking permissions for user {}", authentication.getName());
            User user = userService.getUserByEmail(emailDecoded).orElseThrow(() -> new RuntimeException("User with email " + email + " was not found."));

            return user.getRole().getPermissions().stream().anyMatch(
                    permission -> permission.getPermissionResource().equals(resource)
                    &&  permission.getPermissionAction().equals(action)
            );

        }
        catch(Exception e){
            log.error("Error while checking permissions", e);
           return false;
        }
    }
}
