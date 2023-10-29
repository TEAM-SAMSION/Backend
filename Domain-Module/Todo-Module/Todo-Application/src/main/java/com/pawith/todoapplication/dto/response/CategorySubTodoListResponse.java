package com.pawith.todoapplication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategorySubTodoListResponse {
    private List<CategorySubTodoResponse> todos;
}
