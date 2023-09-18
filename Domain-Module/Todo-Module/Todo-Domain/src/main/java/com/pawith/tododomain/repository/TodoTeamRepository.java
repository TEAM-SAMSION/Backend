package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoTeamRepository extends JpaRepository<TodoTeam, Long> {
    Optional<TodoTeam> findByTeamCode(String teamCode);

    Boolean existsByTeamCode(String teamCode);
}
