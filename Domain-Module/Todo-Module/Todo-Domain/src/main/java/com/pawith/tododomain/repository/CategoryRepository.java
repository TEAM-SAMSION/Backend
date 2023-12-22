package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Category;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryQueryRepository {

}
