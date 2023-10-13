package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryListResponse {
    private final List<CategoryInfoResponse> categories;
}
