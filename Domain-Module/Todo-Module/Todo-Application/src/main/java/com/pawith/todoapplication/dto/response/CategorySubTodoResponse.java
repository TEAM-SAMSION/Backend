package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.CompletionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategorySubTodoResponse {
    private Long todoId;
    private String task;
    private CompletionStatus completionStatus;
    private List<AssignUserInfoResponse> assignNames;
    private Boolean isAssigned;
}
