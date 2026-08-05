package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.Task;
import com.example.demo.tasks.domain.model.TaskComment;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TaskCommentMapper;
import com.example.demo.tasks.dto.request.TaskComment.CreateTaskCommentRequest;
import com.example.demo.tasks.dto.request.TaskComment.UpdateTaskCommentRequest;
import com.example.demo.tasks.dto.response.TaskComment.TaskCommentResponse;
import com.example.demo.tasks.exception.CommentNotFoundException;
import com.example.demo.tasks.exception.TaskNotFoundException;
import com.example.demo.tasks.exception.UnauthorizedException;
import com.example.demo.tasks.repository.TaskCommentRepository;
import com.example.demo.tasks.repository.TaskRepository;
import com.example.demo.tasks.repository.TeamMemberRepository;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCommentService {
    private final TaskCommentRepository taskCommentRepository;
    private final TaskRepository taskRepository;
    private final LoggedInUser loggedInUser;
    private final TeamMemberRepository teamMemberRepository;
    private final TaskCommentMapper taskCommentMapper;

    @Transactional
    public TaskCommentResponse createComment(Long taskId, CreateTaskCommentRequest request) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        User loggedUser = loggedInUser.get();

        checkTeamMember(task, loggedUser);

        TaskComment comment = taskCommentMapper.toEntity(request);
        comment.setTask(task);
        comment.setUser(loggedUser);

        if (request.parentCommentId() != null) {
            TaskComment parent = taskCommentRepository.findById(request.parentCommentId()).orElseThrow(() -> new CommentNotFoundException(request.parentCommentId()));

            // reply-ul trebuie sa fie la un comentariu al aceluiasi task
            if (!parent.getTask().getTaskId().equals(taskId)) {
                throw new IllegalArgumentException("Parent comment does not belong to this task.");
            }

            // nu permit reply la reply
            if (parent.getParentComment() != null) {
                throw new IllegalArgumentException("Replies to replies are not allowed.");
            }

            comment.setParentComment(parent);
        }

        TaskComment saved = taskCommentRepository.save(comment);

        return taskCommentMapper.toResponse(saved);
    }


    public List<TaskCommentResponse> getComments(Long taskId) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        User loggedUser = loggedInUser.get();

        checkTeamMember(task, loggedUser);

        //lista de comentarii principale (fara reply-uri)
        List<TaskComment> comments = taskCommentRepository.findByTaskTaskIdAndParentCommentIsNullOrderByCreationDateAsc(taskId);

        //pentru fircare comentariu iau reply-urile din bd
        return comments.stream()
                .map(comment -> {
                    List<TaskCommentResponse> replies =
                            taskCommentRepository.findByParentCommentCommentIdOrderByCreationDateAsc(comment.getCommentId())
                                    .stream()
                                    .map(taskCommentMapper::toResponse)
                                    .toList();

                    return taskCommentMapper.toResponse(comment, replies);
                })
                .toList();
    }


    @Transactional
    public TaskCommentResponse updateComment(Long taskId, Long commentId, UpdateTaskCommentRequest request) {

        TaskComment comment = taskCommentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getTask().getTaskId().equals(taskId)) {
            throw new CommentNotFoundException(commentId);
        }

        User loggedUser = loggedInUser.get();

        if (!comment.getUser().getUserId().equals(loggedUser.getUserId())) {
            throw new UnauthorizedException("You can only edit your own comments.");
        }

        if (comment.isDeleted()) {
            throw new IllegalArgumentException("Deleted comments cannot be edited.");
        }

        comment.setContent(request.content());

        TaskComment updatedComment = taskCommentRepository.save(comment);

        return taskCommentMapper.toResponse(updatedComment);
    }


    @Transactional
    public void deleteComment(Long taskId, Long commentId) {
        TaskComment comment = taskCommentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));

        if (!comment.getTask().getTaskId().equals(taskId)) {
            throw new CommentNotFoundException(commentId);
        }

        User loggedUser = loggedInUser.get();

        if (!comment.getUser().getUserId().equals(loggedUser.getUserId())) {
            throw new UnauthorizedException("You can only delete your own comments.");
        }

        comment.setDeleted(true);

        taskCommentRepository.save(comment);
    }


    private void checkTeamMember(Task task, User user) {
        if (task.getTeam() == null) {
            throw new UnauthorizedException("Comments are only available for team tasks.");
        }

        boolean isMember = teamMemberRepository.existsByTeamTeamIdAndUserUserId(task.getTeam().getTeamId(), user.getUserId());

        if (!isMember) {
            throw new UnauthorizedException("Only team members can access comments for this task.");
        }
    }
}
