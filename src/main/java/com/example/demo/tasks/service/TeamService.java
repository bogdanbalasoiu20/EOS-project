package com.example.demo.tasks.service;

import com.example.demo.tasks.domain.model.Team;
import com.example.demo.tasks.domain.model.User;
import com.example.demo.tasks.dto.mapper.TeamMapper;
import com.example.demo.tasks.dto.request.Team.CreateTeamRequest;
import com.example.demo.tasks.dto.request.Team.UpdateTeamRequest;
import com.example.demo.tasks.dto.response.Team.TeamResponse;
import com.example.demo.tasks.exception.TeamAlreadyExistsException;
import com.example.demo.tasks.exception.TeamNotFoundException;
import com.example.demo.tasks.repository.TeamRepository;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final LoggedInUser loggedInUser;

    public List<TeamResponse> getTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::toResponse)
                .toList();
    }

    public TeamResponse getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        return teamMapper.toResponse(team);
    }

    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request) {
        if (teamRepository.existsByTeamName(request.teamName())) {
            throw new TeamAlreadyExistsException(request.teamName());
        }

        Team team = teamMapper.toEntity(request);
        User user = loggedInUser.get();

        team.setCreatedBy(user.getUsername());
        team.setLastUpdatedBy(user.getUsername());

        Team savedTeam = teamRepository.save(team);

        return teamMapper.toResponse(savedTeam);
    }

    public TeamResponse updateTeam(Long teamId, UpdateTeamRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        if (request.teamName() != null) {
            team.setTeamName(request.teamName());
        }

        if (request.description() != null) {
            team.setDescription(request.description());
        }

        Team updatedTeam = teamRepository.save(team);

        return teamMapper.toResponse(updatedTeam);
    }

    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(teamId));

        teamRepository.delete(team);
    }
}
