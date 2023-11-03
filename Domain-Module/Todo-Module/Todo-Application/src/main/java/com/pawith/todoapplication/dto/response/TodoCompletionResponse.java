package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.CompletionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoCompletionResponse {
    private CompletionStatus completionStatus;
}
