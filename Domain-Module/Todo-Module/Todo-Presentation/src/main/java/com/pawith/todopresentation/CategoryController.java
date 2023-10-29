package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.CategoryInfoListResponse;
import com.pawith.todoapplication.service.CategoryGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryGetUseCase categoryGetUseCase;

    @GetMapping("/teams/{todoTeamId}/category")
    public CategoryInfoListResponse getCategoryList(@PathVariable Long todoTeamId){
        return categoryGetUseCase.getCategoryList(todoTeamId);
    }
}
