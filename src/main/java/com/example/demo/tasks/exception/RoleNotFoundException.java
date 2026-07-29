package com.example.demo.tasks.exception;

import com.example.demo.tasks.domain.enums.RoleName;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(RoleName roleName) {
        super("Role not found: " + roleName);
    }
}