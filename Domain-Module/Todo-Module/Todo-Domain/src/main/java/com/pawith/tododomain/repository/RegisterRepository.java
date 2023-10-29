package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    @Query("select r from Register r join fetch r.todoTeam where r.userId = :userId and r.isRegistered = true")
    Slice<Register> findAllByUserId(Long userId, Pageable pageable);

    List<Register> findAllByTodoTeamId(Long todoTeamId);

    @Query("select r from Register r where r.id in :ids")
    List<Register> findAllByIds(@Param("ids") List<Long> ids);

    @Query("select r from Register r join Assign a on a.id=:todoId where a.register.id = r.id")
    List<Register> findByTodoId(Long todoId);

    @Query("select r from Register r join Category c on c.id = :categoryId where c.todoTeam.id= r.todoTeam.id")
    List<Register> findAllByCategoryId(Long categoryId);

    Optional<Register> findByTodoTeamIdAndUserId(Long todoTeamId, Long userId);

    Optional<Register> findByTodoTeamIdAndAuthority(Long todoTeamId, Authority authority);

    @Query("select count(r) from Register r where r.todoTeam.id = :todoTeamId and r.isRegistered = true")
    Integer countByTodoTeamId(Long todoTeamId);

    Boolean existsByTodoTeamIdAndUserIdAndIsRegistered(Long todoTeamId, Long userId, boolean isRegistered);
}
