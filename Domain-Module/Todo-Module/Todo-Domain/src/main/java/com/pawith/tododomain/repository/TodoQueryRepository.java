package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoQueryRepository {
    List<Todo> findTodoListByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate moveDate);
}
