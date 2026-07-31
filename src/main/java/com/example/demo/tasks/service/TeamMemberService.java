package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.Team;
import com.example.demo.tasks.domain.model.TeamMember;
import com.example.demo.tasks.domain.model.TeamMemberId;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TeamMemberMapper;
import com.example.demo.tasks.dto.request.TeamMember.AddTeamMemberRequest;
import com.example.demo.tasks.dto.response.TeamMember.TeamMemberResponse;
import com.example.demo.tasks.exception.TeamMemberAlreadyExistsException;
import com.example.demo.tasks.exception.TeamMemberNotFoundException;
import com.example.demo.tasks.exception.TeamNotFoundException;
import com.example.demo.tasks.exception.UserNotFoundException;
import com.example.demo.tasks.repository.TeamMemberRepository;
import com.example.demo.tasks.repository.TeamRepository;
import com.example.demo.tasks.repository.UserRepository;
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


    public TeamMemberResponse addMember(Long teamId, AddTeamMemberRequest request){
        User user = userRepository.findById(request.userId()).orElseThrow(()->new UserNotFoundException("User not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(()->new TeamNotFoundException(teamId));

        TeamMember member = TeamMember.builder()
                .id(new TeamMemberId(teamId, request.userId()))
                .team(team)
                .user(user)
                .build();

        if (teamMemberRepository.existsByTeamTeamIdAndUserUserId(teamId, request.userId())) {
            throw new TeamMemberAlreadyExistsException(teamId, request.userId());
        }

        teamMemberRepository.save(member);

        return teamMemberMapper.toResponse(member);
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
