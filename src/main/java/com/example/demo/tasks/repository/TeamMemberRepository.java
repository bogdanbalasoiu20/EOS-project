package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.TeamMember;
import com.example.demo.tasks.domain.model.TeamMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamMemberId> {
    boolean existsByTeamTeamIdAndUserUserId(Long teamId, Long userId);
    List<TeamMember> findByTeamTeamId(Long teamId);
    boolean existsByTeamTeamId(Long teamId);
    List<TeamMember> findByUserUserId(Long userId);
}