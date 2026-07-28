package com.example.demo.config;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.service.TaskService;
import com.example.demo.tasks.service.UserService;
import com.example.demo.utils.LoggedInUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Slf4j
@Component("checkPermissions")
public class PermissionChecker {
    private final LoggedInUser loggedInUser;

    public PermissionChecker(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean hasPermission(String resource, String action) {
        try {
            User user = loggedInUser.get();

            log.info("Checking permissions for user {}", user.getEmail());

            return user.getRole()
                    .getPermissions()
                    .stream()
                    .anyMatch(permission ->
                            permission.getPermissionResource().equals(resource)
                                    && permission.getPermissionAction().equals(action));

        } catch (Exception e) {
            log.error("Error while checking permissions", e);
            return false;
        }
    }
}
