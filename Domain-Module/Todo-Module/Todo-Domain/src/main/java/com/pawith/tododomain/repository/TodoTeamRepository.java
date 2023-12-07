package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoTeamRepository extends JpaRepository<TodoTeam, Long>, TodoTeamQueryRepository {
    Optional<TodoTeam> findByTeamCode(String teamCode);

    Boolean existsByTeamCode(String teamCode);
}
