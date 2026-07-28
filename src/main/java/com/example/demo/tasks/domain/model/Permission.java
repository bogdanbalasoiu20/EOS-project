package com.example.demo.tasks.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERMISSIONS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMISSION_ID")
    private Long id;

    @Column(name = "PERMISSION_ACTION", nullable = false)
    private String permissionAction;

    @Column(name = "PERMISSION_RESOURCE", nullable = false)
    private String permissionResource;
}
