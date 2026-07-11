package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.StatusType;
import com.example.demo.tasks.dto.StatusTypeDto;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeMapper {
    public StatusTypeDto toDto(StatusType statusType) {
        return StatusTypeDto.builder()
                .statusTypeId(statusType.getStatusTypeId())
                .statusName(statusType.getStatusName())
                .createdBy(statusType.getCreatedBy())
                .creationDate(statusType.getCreationDate())
                .build();
    }

    public StatusType toEntity(StatusTypeDto statusTypeDto) {
        return StatusType.builder()
                .statusName(statusTypeDto.getStatusName())
                .createdBy(statusTypeDto.getCreatedBy())
                .build();
    }
}
