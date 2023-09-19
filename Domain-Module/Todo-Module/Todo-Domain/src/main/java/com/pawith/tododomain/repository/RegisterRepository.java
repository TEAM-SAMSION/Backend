package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Register;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    Slice<Register> findAllByUserId(Long userId, Pageable pageable);

    Optional<Register> findByTodoTeamIdAndUserId(Long todoTeamId, Long userId);

    List<Register> findAllByUserId(Long userId);

    Boolean existsByTodoTeamIdAndUserId(Long todoTeamId, Long userId);
}
