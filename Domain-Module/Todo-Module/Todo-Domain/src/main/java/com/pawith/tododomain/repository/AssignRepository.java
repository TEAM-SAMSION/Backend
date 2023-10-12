package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AssignRepository extends JpaRepository<Assign, Long> {

    @Query("select a " +
        "from Assign a " +
        "join Category c on c.id=:categoryId "+
        "join fetch a.register " +
        "join fetch a.todo t " +
        "where c.id=t.category.id and t.scheduledDate=:scheduledDate")
    List<Assign> findAllByCategoryIdAndScheduledDate(Long categoryId, LocalDate scheduledDate);
}
