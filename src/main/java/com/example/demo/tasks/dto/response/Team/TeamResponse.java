package com.example.demo.tasks.dto.response.Team;


public record TeamResponse(
        Long teamId,
        String teamName,
        String description,
        Long teamLeaderId,
        String teamLeaderUsername
) {}