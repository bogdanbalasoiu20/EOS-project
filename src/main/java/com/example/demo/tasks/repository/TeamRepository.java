package com.example.demo.tasks.repository;

import com.example.demo.tasks.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String teamName);
    boolean existsByTeamName(String teamName);
}
