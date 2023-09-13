package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoTeamRepository extends JpaRepository<TodoTeam, Long> {
}
