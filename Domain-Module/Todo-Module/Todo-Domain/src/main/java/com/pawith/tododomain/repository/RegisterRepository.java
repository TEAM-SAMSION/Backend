package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long>, RegisterQueryRepository {

    List<Register> findAllByTodoTeamId(Long todoTeamId);

    List<Register> findAllByUserId(Long userId);

    Optional<Register> findByTodoTeamIdAndUserId(Long todoTeamId, Long userId);

    Optional<Register> findByTodoTeamIdAndAuthority(Long todoTeamId, Authority authority);

    Boolean existsByTodoTeamIdAndUserIdAndIsRegistered(Long todoTeamId, Long userId, boolean isRegistered);

}
