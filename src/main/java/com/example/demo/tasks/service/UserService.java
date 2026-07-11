package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.UserMapper;
import com.example.demo.tasks.dto.request.User.CreateUserRequest;
import com.example.demo.tasks.dto.request.User.UpdateUserRequest;
import com.example.demo.tasks.dto.response.User.UserResponse;
import com.example.demo.tasks.exception.UserAlreadyExistsException;
import com.example.demo.tasks.exception.UserNotFoundException;
import com.example.demo.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        log.info("Retrieving all users");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(findUser(id));
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException(request.username());
        }

        User user = userMapper.toEntity(request);

        return userMapper.toResponse(userRepository.save(user));
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

    public void deleteUser(Long id) {
        userRepository.delete(findUser(id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
