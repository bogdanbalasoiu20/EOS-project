package com.example.demo.tasks.domain.model;

import com.example.demo.tasks.domain.TaskStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Task {

    private Long id;
    private String content;
    private LocalDateTime dueDate;
    private TaskStatus status;

}
