package com.example.demo.tasks.dto.request.Team;


import jakarta.validation.constraints.NotBlank;

public record UpdateTeamRequest(
        @NotBlank
        String teamName,
        String description
) {
}
