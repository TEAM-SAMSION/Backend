package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Long countByCategoryIdAndCreatedAt(Long categoryId, LocalDateTime createdAt);

    Long countByCategoryIdAndCreatedAtAndTodoStatus(Long categoryId, LocalDateTime createdAt, String status);
}
