package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryManageInfoResponse {
    private Long categoryId;
    private String categoryName;
    private CategoryStatus categoryStatus;
}
