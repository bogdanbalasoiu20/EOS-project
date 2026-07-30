package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.Team;
import com.example.demo.tasks.dto.request.Team.CreateTeamRequest;
import com.example.demo.tasks.dto.response.Team.TeamResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team toEntity(CreateTeamRequest request) {
        return Team.builder()
                .teamName(request.teamName())
                .description(request.description())
                .build();
    }

    public TeamResponse toResponse(Team team) {
        return new TeamResponse(
                team.getTeamId(),
                team.getTeamName(),
                team.getDescription(),
                team.getTeamLeader() != null ? team.getTeamLeader().getUserId() : null,
                team.getTeamLeader() != null ? team.getTeamLeader().getUsername() : null
        );
    }
}