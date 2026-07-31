package com.example.demo.tasks.controller;


import com.example.demo.tasks.dto.request.TeamMember.AddTeamMemberRequest;
import com.example.demo.tasks.dto.response.TeamMember.TeamMemberResponse;
import com.example.demo.tasks.service.TeamMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @PostMapping("/{teamId}/members")
    public ResponseEntity<TeamMemberResponse> addMember(@PathVariable Long teamId, @RequestBody @Valid AddTeamMemberRequest request) {
        return ResponseEntity.ok(teamMemberService.addMember(teamId, request));
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<TeamMemberResponse>> getMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamMemberService.getMembers(teamId));
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        teamMemberService.removeMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }

}
