package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.exception.CategoryNotFoundException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.CategoryRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategoryListByTodoTeamIdAndStatus(Long todoTeamId, LocalDate moveDate) {
        return categoryRepository.findAllByTodoTeamIdAndCategoryStatusQuery(todoTeamId, moveDate);
    }

    public List<Category> findCategoryListByTodoTeamId(Long todoTeamId) {
        return categoryRepository.findAllByTodoTeamIdQuery(todoTeamId);
    }

    public Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(TodoError.CATEGORY_NOT_FOUND));
    }
}
