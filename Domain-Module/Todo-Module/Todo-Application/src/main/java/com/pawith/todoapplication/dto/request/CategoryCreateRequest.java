package com.pawith.todoapplication.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryCreateRequest {
    private String categoryName;

    public CategoryCreateRequest(String categoryName) {
        this.categoryName = categoryName;
    }
}
