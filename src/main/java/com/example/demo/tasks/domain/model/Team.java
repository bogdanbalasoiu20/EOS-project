package com.example.demo.tasks.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "teams")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_leader_id")
    private User teamLeader;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "created_by_fullname")
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