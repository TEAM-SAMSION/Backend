package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Register;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    @Query("select r from Register r join fetch r.todoTeam where r.userId = :userId")
    Slice<Register> findAllByUserId(Long userId, Pageable pageable);

    Optional<Register> findByTodoTeamIdAndUserId(Long todoTeamId, Long userId);

    Boolean existsByTodoTeamIdAndUserId(Long todoTeamId, Long userId);

    List<Register> findAllByTodoTeamId(Long todoTeamId);

    @Query("select r from Register r where r.id in :ids")
    List<Register> findAllByIds(@Param("ids") List<Long> ids);
}
