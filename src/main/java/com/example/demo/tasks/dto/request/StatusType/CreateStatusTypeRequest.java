package com.example.demo.tasks.dto.request.StatusType;

import jakarta.validation.constraints.NotBlank;

public record CreateStatusTypeRequest(
        @NotBlank(message = "Status name is required")
        String statusName,

        @NotBlank(message = "Created by is required")
        String createdBy
) {}
