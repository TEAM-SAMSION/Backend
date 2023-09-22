package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AssignRepository extends JpaRepository<Assign, Long> {

    @Query(value = "select a from Assign a " +
            "join Register r on r.userId=:userId and r.todoTeam.id=:todoTeamId " +
            "join Assign  a on a.register.id=r.id " +
            "where date(a.createdAt) = date(now())")
    Slice<Assign> findTodayAssign(@Param("userId") Long userId, @Param("todoTeamId") Long todoTeamId, Pageable pageable);
}
