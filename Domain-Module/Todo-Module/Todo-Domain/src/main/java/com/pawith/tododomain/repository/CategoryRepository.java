package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByTodoTeamId(Long todoTeamId);
}
