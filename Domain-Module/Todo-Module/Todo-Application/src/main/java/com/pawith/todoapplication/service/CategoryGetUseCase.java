package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.todoapplication.dto.response.CategoryListResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.service.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryGetUseCase {

    private final CategoryQueryService categoryQueryService;

    public CategoryListResponse getCategoryList(final Long todoTeamId) {
        List<Category> categoryList = categoryQueryService.findCategoryListByTodoTeamIdAndStatus(todoTeamId);
        List<CategoryInfoResponse> categorySimpleResponses = categoryList.stream()
            .map(category -> new CategoryInfoResponse(category.getId(), category.getName()))
            .collect(Collectors.toList());
        return new CategoryListResponse(categorySimpleResponses);
    }
}