package com.example.demo.tasks.dto.response.StatusType;

import java.time.LocalDateTime;

public record StatusTypeResponse(
        String statusTypeId,
        String statusName,
        String createdBy,
        LocalDateTime creationDate,
        String lastUpdatedBy,
        LocalDateTime lastUpdateDate,
        String createdByFullname
) {}