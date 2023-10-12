package com.pawith.todopresentation;

import com.pawith.todoapplication.dto.response.CategoryListResponse;
import com.pawith.todoapplication.service.CategoryGetUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryGetUseCase categoryGetUseCase;

    @GetMapping("/{teamId}")
    public CategoryListResponse getCategoryList(@PathVariable Long teamId){
        return categoryGetUseCase.getCategoryList(teamId);
    }
}
