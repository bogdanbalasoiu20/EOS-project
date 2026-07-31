package com.example.demo.tasks.dto.request.TeamMember;

import jakarta.validation.constraints.NotNull;

public record AddTeamMemberRequest(
        @NotNull
        Long userId
) {
}
