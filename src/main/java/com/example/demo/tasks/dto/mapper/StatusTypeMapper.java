package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.dto.StatusTypeDto;
import com.example.demo.tasks.dto.request.StatusType.CreateStatusTypeRequest;
import com.example.demo.tasks.dto.response.StatusType.StatusTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeMapper {
    public StatusTypeResponse toResponse(StatusType statusType) {
        return new StatusTypeResponse(
                statusType.getStatusTypeId(),
                statusType.getStatusName(),
                statusType.getCreatedBy(),
                statusType.getCreationDate(),
                statusType.getLastUpdatedBy(),
                statusType.getLastUpdateDate(),
                statusType.getCreatedByFullname()
        );
    }

    public StatusType toEntity(CreateStatusTypeRequest request) {
        return StatusType.builder()
                .statusName(request.statusName())
                .createdBy(request.createdBy())
                .lastUpdatedBy(request.createdBy())
                .build();
    }
}
