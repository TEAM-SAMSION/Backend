package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignRepository extends JpaRepository<Assign, Long> {

    @Query("select a " +
        "from Assign a " +
        "join fetch a.register " +
        "join fetch a.todo t " +
        "where t.category.id=:categoryId and t.scheduledDate=:scheduledDate")
    List<Assign> findAllByCategoryIdAndScheduledDate(Long categoryId, LocalDate scheduledDate);


    @Query("select a " +
            "from Assign a " +
            "join a.register r " +
            "join fetch a.todo t " +
            "join fetch t.category " +
            "where r.userId=:userId and r.todoTeam.id=:todoTeamId and t.scheduledDate=:scheduledDate")
    List<Assign> findAllByUserIdAndTodoTeamIdAndScheduledDate(Long userId, Long todoTeamId, LocalDate scheduledDate);

    @Modifying
    @Query("update Assign a set a.isDeleted = true where a.register.id in (:registerIds)")
    void deleteByRegisterIds(final List<Long> registerIds);

    @Modifying
    @Query("update Assign a set a.isDeleted = true where a.todo.id=:todoId")
    void deleteAllByTodoId(final Long todoId);

    @Query("select a " +
        "from Assign a " +
        "join Register r on r.id = a.register.id and r.userId=:userId " +
        "join Todo t on t.id = a.todo.id and t.id = :todoId")
    Optional<Assign> findByTodoIdAndUserId(Long todoId, Long userId);

    @Query("select a " +
            "from Assign a " +
            "join fetch a.todo t " +
            "where t.id=:todoId")
    List<Assign> findAllByTodoId(Long todoId);
}
