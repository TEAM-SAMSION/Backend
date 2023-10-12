package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignUserInfoResponse {
    private final Long assigneeId;
    private final String assigneeName;
}
