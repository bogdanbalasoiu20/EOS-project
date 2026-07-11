package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, String> {
    boolean existsByStatusName(String statusName);
}
