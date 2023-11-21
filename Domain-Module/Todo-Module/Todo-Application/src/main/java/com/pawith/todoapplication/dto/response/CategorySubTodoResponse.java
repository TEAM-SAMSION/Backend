package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.CompletionStatus;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategorySubTodoResponse {
    private Long todoId;
    private String task;
    private CompletionStatus completionStatus;
    private List<AssignUserInfoResponse> assignNames;
    private Boolean isAssigned;
    private TodoNotificationInfoResponse notificationInfo;
}
