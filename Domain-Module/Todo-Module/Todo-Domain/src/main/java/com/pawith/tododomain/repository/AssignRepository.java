package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssignRepository extends JpaRepository<Assign, Long>, AssignQueryRepository {

}
