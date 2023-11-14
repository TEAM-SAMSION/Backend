package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.exception.CategoryNotFoundException;
import com.pawith.tododomain.exception.TodoError;
import com.pawith.tododomain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findCategoryListByTodoTeamIdAndStatus(Long todoTeamId) {
        return categoryRepository.findAllByTodoTeamIdAndCategoryStatus(todoTeamId);
    }

    public List<Category> findCategoryListByTodoTeamId(Long todoTeamId) {
        return categoryRepository.findAllByTodoTeamId(todoTeamId);
    }

    public Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(TodoError.CATEGORY_NOT_FOUND));
    }
}
