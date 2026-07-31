package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.enums.RoleName;
import com.example.demo.tasks.domain.model.Team;
import com.example.demo.tasks.domain.model.TeamMember;
import com.example.demo.tasks.domain.model.TeamMemberId;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TeamMemberMapper;
import com.example.demo.tasks.dto.request.TeamMember.AddTeamMemberRequest;
import com.example.demo.tasks.dto.response.TeamMember.TeamMemberResponse;
import com.example.demo.tasks.exception.*;
import com.example.demo.tasks.repository.TeamMemberRepository;
import com.example.demo.tasks.repository.TeamRepository;
import com.example.demo.tasks.repository.UserRepository;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final LoggedInUser loggedInUser;


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

    public void removeMember(Long teamId, Long userId) {
        TeamMemberId id = new TeamMemberId(teamId, userId);

        TeamMember member = teamMemberRepository.findById(id).orElseThrow(() -> new TeamMemberNotFoundException(teamId, userId));

        teamMemberRepository.delete(member);
    }
}
