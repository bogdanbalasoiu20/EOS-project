package com.example.demo.tasks.dto.response.TeamMember;

public record TeamMemberResponse(
        Long teamId,
        Long userId,
        String username
) {
}
