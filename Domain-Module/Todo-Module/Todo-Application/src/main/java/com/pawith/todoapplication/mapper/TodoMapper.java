package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.TodoCreateRequest;
import com.pawith.todoapplication.dto.response.AssignUserInfoResponse;
import com.pawith.todoapplication.dto.response.CategorySubTodoResponse;
import com.pawith.todoapplication.dto.response.TodoNotificationInfoResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoMapper {

    public static Todo mapToTodo(TodoCreateRequest request, Category category){
        return Todo.builder()
            .category(category)
            .description(request.getDescription())
            .scheduledDate(request.getScheduledDate())
            .build();
    }

    public static CategorySubTodoResponse mapToCategorySubTodoResponse(Todo todo,
                                                                       List<AssignUserInfoResponse> assignsList,
                                                                       Boolean isAssigned,
                                                                       TodoNotificationInfoResponse todoNotificationInfoResponse){
        return CategorySubTodoResponse.builder()
            .todoId(todo.getId())
            .task(todo.getDescription())
            .completionStatus(todo.getCompletionStatus())
            .assignNames(assignsList)
            .isAssigned(isAssigned)
            .notificationInfo(todoNotificationInfoResponse)
            .build();
    }
}
