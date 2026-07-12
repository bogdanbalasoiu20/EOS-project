package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.TaskStatus;
import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.domain.model.Task;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TaskMapper;
import com.example.demo.tasks.dto.request.Task.AssignTaskRequest;
import com.example.demo.tasks.dto.request.Task.CreateTaskRequest;
import com.example.demo.tasks.dto.request.Task.UpdateTaskRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import com.example.demo.tasks.exception.*;
import com.example.demo.tasks.repository.StatusTypeRepository;
import com.example.demo.tasks.repository.TaskRepository;
import com.example.demo.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private static final String COMPLETED_STATUS = "Completed";

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StatusTypeRepository statusTypeRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> getTasks(String status, String keyword) {
        log.info("Retrieving tasks with status={} and keyword={}", status, keyword);

        List<Task> tasks;

        if (status != null && keyword != null) {
            tasks = taskRepository.findByStatusTypeStatusNameAndTaskNameContainingIgnoreCase(status, keyword);
        } else if (status != null) {
            tasks = taskRepository.findByStatusTypeStatusName(status);
        } else if (keyword != null) {
            tasks = taskRepository.findByTaskNameContainingIgnoreCase(keyword);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream().map(taskMapper::toResponse).toList();
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        StatusType statusType = findStatus(request.statusTypeId());
        Task task = taskMapper.toEntity(request);
        task.setStatusType(statusType);

        if (request.userId() != null) {
            task.setUser(findUser(request.userId()));
        }

        Task savedTask = taskRepository.save(task);
        log.info("Created task with id {}", savedTask.getTaskId());

        return taskMapper.toResponse(savedTask);
    }

    public TaskResponse getTaskById(Long taskId) {
        log.info("Retrieving task {}", taskId);
        return taskMapper.toResponse(findTask(taskId));
    }

    @Transactional
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) {
        Task task = findTask(taskId);

        if (request.taskName() != null) {
            task.setTaskName(request.taskName());
        }

        if (request.dueDate() != null) {
            task.setDueDate(request.dueDate());
        }

        if (request.userId() != null) {
            task.setUser(findUser(request.userId()));
        }

        if (request.statusTypeId() != null) {
            task.setStatusType(findStatus(request.statusTypeId()));
        }

        Task updatedTask = taskRepository.save(task);
        log.info("Updated task {}", taskId);

        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = findTask(taskId);
        taskRepository.delete(task);
        log.info("Deleted task {}", taskId);
    }

    @Transactional
    public TaskResponse completeTask(Long taskId) {
        Task task = findTask(taskId);

        if (COMPLETED_STATUS.equalsIgnoreCase(task.getStatusType().getStatusName())) {
            throw new TaskAlreadyCompletedException(taskId);
        }

        StatusType completedStatus = statusTypeRepository.findByStatusName(COMPLETED_STATUS).orElseThrow(() -> new StatusTypeNotFoundException(COMPLETED_STATUS));

        task.setStatusType(completedStatus);

        Task completedTask = taskRepository.save(task);
        log.info("Completed task {}", taskId);

        return taskMapper.toResponse(completedTask);
    }

    public List<TaskResponse> getOverdueTasks(Long userId) {
        findUser(userId);
        log.info("Retrieving overdue tasks for user {}", userId);

        return taskRepository.findByUserUserIdAndDueDateBefore(userId,LocalDateTime.now())
                .stream()
                .filter(task -> !COMPLETED_STATUS.equalsIgnoreCase(task.getStatusType().getStatusName()))
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> getTodayTasks(Long userId) {
        findUser(userId);
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        log.info("Retrieving today's tasks for user {}", userId);

        return taskRepository.findByUserUserIdAndDueDateBetween(userId,start,end)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> getThisWeekTasks(Long userId) {
        findUser(userId);
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        log.info("Retrieving this week's tasks for user {}", userId);

        return taskRepository.findByUserUserIdAndDueDateBetween(userId,startOfWeek.atStartOfDay(),endOfWeek.atTime(LocalTime.MAX))
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse assignTask(Long taskId, AssignTaskRequest request) {
        Task task = findTask(taskId);
        User user = findUser(request.userId());

        if (task.getUser() != null && task.getUser().getUserId().equals(user.getUserId())) {

            throw new TaskAlreadyAssignedException(taskId, user.getUserId());
        }

        task.setUser(user);
        Task updatedTask = taskRepository.save(task);
        log.info("Assigned task {} to user {}", taskId, user.getUserId());
        return taskMapper.toResponse(updatedTask);
    }

    public List<TaskResponse> getUnassignedTasks() {
        log.info("Retrieving unassigned tasks");

        return taskRepository.findByUserIsNull()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }



    private Task findTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private StatusType findStatus(String id) {
        return statusTypeRepository.findById(id).orElseThrow(() -> new StatusTypeNotFoundException(id));
    }
}
