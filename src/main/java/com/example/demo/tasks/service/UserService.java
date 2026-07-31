package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.Role;
import com.example.demo.tasks.domain.model.Team;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TaskMapper;
import com.example.demo.tasks.dto.mapper.UserMapper;
import com.example.demo.tasks.dto.request.User.UpdateRoleRequest;
import com.example.demo.tasks.dto.request.User.UpdateUserRequest;
import com.example.demo.tasks.dto.response.Task.TaskResponse;
import com.example.demo.tasks.dto.response.User.UserResponse;
import com.example.demo.tasks.dto.response.User.UserTaskCountResponse;
import com.example.demo.tasks.exception.BadRequestException;
import com.example.demo.tasks.exception.RoleNotFoundException;
import com.example.demo.tasks.exception.TeamNotFoundException;
import com.example.demo.tasks.exception.UserNotFoundException;
import com.example.demo.tasks.repository.*;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final RoleRepository roleRepository;
    private final LoggedInUser loggedInUser;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    public List<UserResponse> getUsers(String keyword) {
        log.info("Retrieving users with keyword={}", keyword);
        List<User> users;

        if (keyword != null && !keyword.isBlank()) {
            users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword,keyword);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(findUser(id));
    }


    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = findUser(id);

        if (request.username() != null) {
            user.setUsername(request.username());
        }

        if (request.birthDate() != null) {
            user.setBirthDate(request.birthDate());
        }

        if (request.internal() != null) {
            user.setInternal(request.internal());
        }

        return userMapper.toResponse(userRepository.save(user));
    }


    public UserResponse updateRole(Long userId, UpdateRoleRequest request) {
        User loggedUser = loggedInUser.get();

        if (loggedUser.getUserId().equals(userId)) {
            throw new BadRequestException("You cannot change your own role.");
        }

        User user = findUser(userId);

        Role role = roleRepository.findByRoleName(request.role()).orElseThrow(() -> new RoleNotFoundException(request.role()));

        user.setRole(role);

        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.delete(findUser(id));
    }

    public List<UserResponse> getUsersWithoutTasks() {
        log.info("Retrieving users without tasks");

        return userRepository.findUsersWithoutTasks()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public List<TaskResponse> getUserTasks(Long userId) {
        findUser(userId);
        return taskRepository.findByUserUserId(userId)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public UserTaskCountResponse getTaskCount(Long userId) {
        findUser(userId);
        long count = taskRepository.countByUserUserId(userId);

        return new UserTaskCountResponse(userId, count);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserResponse> getUsersNotInTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        List<Long> memberIds = teamMemberRepository.findByTeamTeamId(teamId)
                .stream()
                .map(member -> member.getUser().getUserId())
                .toList();

        return userRepository.findAll()
                .stream()
                .filter(user -> !memberIds.contains(user.getUserId()))
                .map(userMapper::toResponse)
                .toList();
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
