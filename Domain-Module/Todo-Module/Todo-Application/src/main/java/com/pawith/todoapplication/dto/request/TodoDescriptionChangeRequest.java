package com.pawith.todoapplication.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoDescriptionChangeRequest {
    private String description;

    public TodoDescriptionChangeRequest(String description) {
        this.description = description;
    }
}
