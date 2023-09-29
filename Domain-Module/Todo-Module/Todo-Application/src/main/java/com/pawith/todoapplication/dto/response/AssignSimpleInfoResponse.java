package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignSimpleInfoResponse {
    private final Long assigneeId;
    private final String assigneeName;
}
