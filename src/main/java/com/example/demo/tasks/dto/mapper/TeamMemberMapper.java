package com.example.demo.tasks.dto.mapper;

import com.example.demo.tasks.domain.model.TeamMember;
import com.example.demo.tasks.dto.response.TeamMember.TeamMemberResponse;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberMapper {
    public TeamMemberResponse toResponse(TeamMember teamMember) {
        return new TeamMemberResponse(
                teamMember.getTeam().getTeamId(),
                teamMember.getUser().getUserId(),
                teamMember.getUser().getUsername()
        );
    }
}
