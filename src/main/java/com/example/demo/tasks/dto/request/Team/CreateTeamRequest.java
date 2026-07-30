package com.example.demo.tasks.dto.request.Team;

import jakarta.validation.constraints.NotBlank;

public record CreateTeamRequest(
        @NotBlank
        String teamName,
        String description,
        Long teamLeaderId
) {}