package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoTeamRepository extends JpaRepository<TodoTeam, Long> {
    Optional<TodoTeam> findByTeamCode(String teamCode);

    Boolean existsByTeamCode(String teamCode);

    @Query("select t from TodoTeam t join Register r on r.userId = :userId where r.todoTeam.id = t.id and r.isRegistered = true")
    List<TodoTeam> findAllByUserId(@Param("userId") Long userId);
}
