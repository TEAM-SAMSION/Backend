package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Assign;

import java.time.LocalDate;
import java.util.List;

public interface AssignQueryRepository {
    List<Assign> findAllByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate scheduledDate);
}
