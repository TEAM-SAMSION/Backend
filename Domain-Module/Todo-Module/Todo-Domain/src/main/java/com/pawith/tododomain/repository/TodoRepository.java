package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
