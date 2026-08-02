package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.enums.RoleName;
import com.example.demo.tasks.domain.model.*;
import com.example.demo.tasks.dto.mapper.TeamMemberMapper;
import com.example.demo.tasks.dto.request.TeamMember.AddTeamMemberRequest;
import com.example.demo.tasks.dto.response.TeamMember.TeamMemberResponse;
import com.example.demo.tasks.exception.*;
import com.example.demo.tasks.repository.TaskRepository;
import com.example.demo.tasks.repository.TeamMemberRepository;
import com.example.demo.tasks.repository.TeamRepository;
import com.example.demo.tasks.repository.UserRepository;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final LoggedInUser loggedInUser;
    private final TaskRepository taskRepository;


    public TeamMemberResponse addMember(Long teamId, AddTeamMemberRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        User loggedUser = loggedInUser.get();

        if (loggedUser.getRole().getRoleName() != RoleName.ADMIN && (team.getTeamLeader() == null || !team.getTeamLeader().getUserId().equals(loggedUser.getUserId()))) {
            throw new UnauthorizedException("Only the team leader can add members to this team.");
        }

        User user = userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (teamMemberRepository.existsByTeamTeamIdAndUserUserId(teamId, request.userId())) {
            throw new TeamMemberAlreadyExistsException(teamId, request.userId());
        }

        TeamMember member = TeamMember.builder()
                .id(new TeamMemberId(teamId, request.userId()))
                .team(team)
                .user(user)
                .build();

        TeamMember savedMember = teamMemberRepository.save(member);

        return teamMemberMapper.toResponse(savedMember);
    }


    public List<TeamMemberResponse> getMembers(Long teamId) {
        teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        return teamMemberRepository.findByTeamTeamId(teamId)
                .stream()
                .map(teamMemberMapper::toResponse)
                .toList();
    }

    @Transactional
    public void removeMember(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));
        User loggedUser = loggedInUser.get();

        boolean isAdmin = loggedUser.getRole().getRoleName() == RoleName.ADMIN;
        boolean isTeamLeader = team.getTeamLeader() != null && team.getTeamLeader().getUserId().equals(loggedUser.getUserId());

        if (!isAdmin && !isTeamLeader) {
            throw new UnauthorizedException("Only the team leader or an admin can remove members from this team.");
        }

        // Team leader-ul nu poate fi eliminat din propria echipa
        if (team.getTeamLeader() != null && team.getTeamLeader().getUserId().equals(userId)) {
            throw new UnauthorizedException("The team leader cannot be removed from the team.");
        }

        TeamMemberId id = new TeamMemberId(teamId, userId);

        TeamMember member = teamMemberRepository.findById(id).orElseThrow(() -> new TeamMemberNotFoundException(teamId, userId));

        List<Task> tasks = taskRepository.findByTeamTeamIdAndUserUserId(teamId, userId);

        // taskurile sunt marcate ca unassigned cand sterg un membru din echipa
        for (Task task : tasks) {
            task.setUser(null);
        }

        taskRepository.saveAll(tasks);

        teamMemberRepository.delete(member);
    }
}
