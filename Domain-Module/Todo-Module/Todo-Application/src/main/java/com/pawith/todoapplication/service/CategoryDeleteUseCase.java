package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.tododomain.service.CategoryDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class CategoryDeleteUseCase {

    private final CategoryDeleteService categoryDeleteService;

    public void deleteCategory(Long categoryId) {
        categoryDeleteService.deleteCategory(categoryId);
    }
}
