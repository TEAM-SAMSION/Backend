package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
