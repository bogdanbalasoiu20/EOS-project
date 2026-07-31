package com.example.demo.tasks.controller;

import com.example.demo.tasks.dto.request.Team.CreateTeamRequest;
import com.example.demo.tasks.dto.request.Team.UpdateTeamRequest;
import com.example.demo.tasks.dto.response.Team.TeamResponse;
import com.example.demo.tasks.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long teamId, @RequestBody UpdateTeamRequest request) {
        return ResponseEntity.ok(teamService.updateTeam(teamId, request));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my/leading")
    public ResponseEntity<List<TeamResponse>> getLeadingTeams() {
        return ResponseEntity.ok(teamService.getLeadingTeams());
    }

    @GetMapping("/my/member")
    public ResponseEntity<List<TeamResponse>> getMemberTeams() {
        return ResponseEntity.ok(teamService.getMemberTeams());
    }
}
