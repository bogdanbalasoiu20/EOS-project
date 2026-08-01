package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserUserId(Long userId);
    long countByUserUserId(Long userId);
    List<Task> findByUserUserIdAndDueDateBefore(Long userId, LocalDateTime dueDate);
    List<Task> findByUserUserIdAndDueDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<Task> findByUserIsNull();
    List<Task> findByTeamTeamIdAndUserUserId(Long teamId, Long userId);
}
