package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.request.User.CreateUserRequest;
import com.example.demo.tasks.dto.response.User.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getBirthDate(),
                user.getInternal(),
                user.getCreatedBy(),
                user.getCreationDate(),
                user.getLastUpdatedBy(),
                user.getLastUpdateDate(),
                user.getCreatedByFullname()
        );
    }

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .birthDate(request.birthDate())
                .internal(0)
                .createdBy("SYSTEM")
                .lastUpdatedBy("SYSTEM")
                .build();
    }

}
