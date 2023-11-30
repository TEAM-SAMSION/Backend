package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.todoapplication.dto.response.CategoryManageInfoResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.service.CategoryQueryService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryGetUseCase {

    private final CategoryQueryService categoryQueryService;

    public ListResponse<CategoryInfoResponse> getCategoryList(final Long todoTeamId, LocalDate moveDate) {
        List<Category> categoryList = categoryQueryService.findCategoryListByTodoTeamIdAndStatus(todoTeamId, moveDate);
        List<CategoryInfoResponse> categorySimpleResponses = categoryList.stream()
            .map(category -> new CategoryInfoResponse(category.getId(), category.getName()))
            .collect(Collectors.toList());
        return ListResponse.from(categorySimpleResponses);
    }

    public ListResponse<CategoryManageInfoResponse> getManageCategoryList(final Long todoTeamId) {
        List<Category> categoryList = categoryQueryService.findCategoryListByTodoTeamId(todoTeamId);
        List<CategoryManageInfoResponse> categoryManageInfoResponses = categoryList.stream()
                .map(category -> new CategoryManageInfoResponse(category.getId(), category.getName(), category.getCategoryStatus()))
                .collect(Collectors.toList());
        return ListResponse.from(categoryManageInfoResponses);
    }
}