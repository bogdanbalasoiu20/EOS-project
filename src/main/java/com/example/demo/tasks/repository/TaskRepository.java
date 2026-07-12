package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserUserId(Long userId);
    long countByUserUserId(Long userId);
    List<Task> findByTaskNameContainingIgnoreCase(String keyword);
    List<Task> findByStatusTypeStatusName(String statusName);
    List<Task> findByStatusTypeStatusNameAndTaskNameContainingIgnoreCase(String status, String keyword);
    List<Task> findByUserUserIdAndDueDateBefore(Long userId, LocalDateTime dueDate);
    List<Task> findByUserUserIdAndDueDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
