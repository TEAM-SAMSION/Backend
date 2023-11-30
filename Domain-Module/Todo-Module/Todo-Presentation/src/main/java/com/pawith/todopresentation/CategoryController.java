package com.pawith.todopresentation;

import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.request.CategoryCreateRequest;
import com.pawith.todoapplication.dto.request.CategoryNameChageRequest;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.todoapplication.dto.response.CategoryManageInfoResponse;
import com.pawith.todoapplication.service.CategoryChangeUseCase;
import com.pawith.todoapplication.service.CategoryCreateUseCase;
import com.pawith.todoapplication.service.CategoryDeleteUseCase;
import com.pawith.todoapplication.service.CategoryGetUseCase;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryGetUseCase categoryGetUseCase;
    private final CategoryChangeUseCase categoryChangeUseCase;
    private final CategoryDeleteUseCase categoryDeleteUseCase;
    private final CategoryCreateUseCase categoryCreateUseCase;

    @GetMapping("/teams/{todoTeamId}/category")
    public ListResponse<CategoryInfoResponse> getCategoryList(@PathVariable Long todoTeamId, @RequestParam("moveDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate moveDate){
        return categoryGetUseCase.getCategoryList(todoTeamId, moveDate);
    }

    @GetMapping("/teams/{todoTeamId}/category/manage")
    public ListResponse<CategoryManageInfoResponse> getCategoryListForManage(@PathVariable Long todoTeamId){
        return categoryGetUseCase.getManageCategoryList(todoTeamId);
    }

    @PutMapping("/teams/category/{categoryId}")
    public void putCategoryStatus(@PathVariable Long categoryId){
        categoryChangeUseCase.changeCategoryStatus(categoryId);
    }

    @DeleteMapping("/teams/category/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId){
        categoryDeleteUseCase.deleteCategory(categoryId);
    }

    @PostMapping("/teams/{todoTeamId}/category")
    public void postCategory(@PathVariable Long todoTeamId, @RequestBody CategoryCreateRequest categoryCreateRequest){
        categoryCreateUseCase.createCategory(todoTeamId, categoryCreateRequest);
    }

    @PutMapping("/teams/category/{categoryId}/name")
    public void putCategoryName(@PathVariable Long categoryId, @RequestBody CategoryNameChageRequest categoryNameChageRequest){
        categoryChangeUseCase.changeCategoryName(categoryId, categoryNameChageRequest);
    }

}
