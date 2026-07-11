package com.example.demo.tasks.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusTypeDto {
    private String statusTypeId;
    private String statusName;
    private String createdBy;
    private LocalDateTime creationDate;
}
