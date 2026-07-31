package com.example.demo.tasks.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TASKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "TASK_NAME", nullable = false)
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_TYPE_ID")
    private StatusType statusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    private LocalDateTime lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    private String lastUpdatedBy;

    @Column(name = "CREATED_BY_FULLNAME")
    private String createdByFullname;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        creationDate = now;
        lastUpdateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdateDate = LocalDateTime.now();
    }
}
