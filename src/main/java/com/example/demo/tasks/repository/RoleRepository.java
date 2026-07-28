package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.enums.RoleName;
import com.example.demo.tasks.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);

}