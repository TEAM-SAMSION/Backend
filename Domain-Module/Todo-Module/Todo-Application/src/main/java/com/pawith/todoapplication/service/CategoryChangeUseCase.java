package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.request.CategoryNameChageRequest;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.CategoryStatus;
import com.pawith.tododomain.service.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class CategoryChangeUseCase {

    private final CategoryQueryService categoryQueryService;

    public void changeCategoryStatus(Long categoryId) {
        Category category = categoryQueryService.findCategoryByCategoryId(categoryId);
        if(category.getCategoryStatus().equals(CategoryStatus.ON) == true){
            category.updateCategoryStatus(CategoryStatus.OFF);
        }
        else {
            category.updateCategoryStatus(CategoryStatus.ON);
        }
    }

    public void changeCategoryName(Long categoryId, CategoryNameChageRequest categoryNameChageRequest) {
        Category category = categoryQueryService.findCategoryByCategoryId(categoryId);
        category.updateCategoryName(categoryNameChageRequest.getCategoryName());
    }
}
