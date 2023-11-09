package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AssignRepository extends JpaRepository<Assign, Long> {

    @Query("select a " +
        "from Assign a " +
        "join fetch a.register " +
        "join fetch a.todo t " +
        "where t.category.id=:categoryId and t.scheduledDate=:scheduledDate")
    List<Assign> findAllByCategoryIdAndScheduledDate(Long categoryId, LocalDate scheduledDate);


    @Query("select a " +
            "from Assign a " +
            "join fetch a.register " +
            "join fetch a.todo t " +
            "where a.register.userId=:userId and a.register.todoTeam.id=:todoTeamId and t.scheduledDate=:scheduledDate")
    List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDate(Long userId, Long todoTeamId, LocalDate scheduledDate);

    @Modifying
    @Query("update Assign a set a.isDeleted = true where a.register.id in (:registerIds)")
    void deleteByRegisterIds(final List<Long> registerIds);

    @Query("select a " +
            "from Assign a " +
            "join fetch a.register " +
            "join fetch a.todo t " +
            "where t.id=:todoId and a.register.userId=:userId")
    Assign findByTodoIdAndUserId(Long todoId, Long userId);

    @Query("select a " +
            "from Assign a " +
            "join fetch a.todo t " +
            "where t.id=:todoId")
    List<Assign> findAllByTodoId(Long todoId);
}
