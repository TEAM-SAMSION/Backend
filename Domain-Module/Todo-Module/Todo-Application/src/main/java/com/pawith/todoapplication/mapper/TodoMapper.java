package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoMapper {

    public static Todo mapToTodo(TodoCreateRequest request, Category category){
        return Todo.builder()
            .category(category)
            .description(request.getDescription())
            .scheduledDate(request.getScheduledDate())
            .build();
    }
}
