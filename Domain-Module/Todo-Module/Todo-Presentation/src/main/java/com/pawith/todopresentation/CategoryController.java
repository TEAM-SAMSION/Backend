package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.CategoryInfoListResponse;
import com.pawith.todoapplication.service.CategoryChangeUseCase;
import com.pawith.todoapplication.service.CategoryGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryGetUseCase categoryGetUseCase;
    private final CategoryChangeUseCase categoryChangeUseCase;

    @GetMapping("/teams/{todoTeamId}/category")
    public CategoryInfoListResponse getCategoryList(@PathVariable Long todoTeamId){
        return categoryGetUseCase.getCategoryList(todoTeamId);
    }

    @PutMapping("/teams/category/{categoryId}")
    public void putCategoryStatus(@PathVariable Long categoryId){
        categoryChangeUseCase.changeCategoryStatus(categoryId);
    }



}
