package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AssignRepository extends JpaRepository<Assign, Long> {
    List<Assign> findAllByRegisterIdAndCreatedAtBetween(Long registerId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
