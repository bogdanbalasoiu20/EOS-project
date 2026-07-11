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
                .birthDate(request.birthDate())
                .internal(request.internal())
                .createdBy(request.createdBy())
                .lastUpdatedBy(request.createdBy())
                .build();
    }

}
