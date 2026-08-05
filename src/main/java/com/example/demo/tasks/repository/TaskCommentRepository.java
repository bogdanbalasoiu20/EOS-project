package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findByTaskTaskIdAndParentCommentIsNullOrderByCreationDateAsc(Long taskId);
    List<TaskComment> findByParentCommentCommentIdOrderByCreationDateAsc(Long parentCommentId);
    void deleteByParentCommentCommentId(Long commentId);
}
