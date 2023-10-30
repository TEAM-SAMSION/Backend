package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.exception.Error;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.exception.CategoryNotFoundException;
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
    public Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(Error.CATEGORY_NOT_FOUND));
    }
}
