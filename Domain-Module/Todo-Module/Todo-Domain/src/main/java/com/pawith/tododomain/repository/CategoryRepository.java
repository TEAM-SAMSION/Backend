package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.todoTeam.id = :todoTeamId and c.categoryStatus = 'ON' order by c.createdAt desc")
    List<Category> findAllByTodoTeamIdAndCategoryStatus(Long todoTeamId);

    @Query("select c from Category c where c.todoTeam.id = :todoTeamId order by c.createdAt desc")
    List<Category> findAllByTodoTeamId(Long todoTeamId);
}
