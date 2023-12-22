package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Category;
import java.time.LocalDate;
import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findAllByTodoTeamIdAndCategoryStatusQuery(Long todoTeamId, LocalDate moveDate);
    List<Category> findAllByTodoTeamIdQuery(Long todoTeamId);
}
