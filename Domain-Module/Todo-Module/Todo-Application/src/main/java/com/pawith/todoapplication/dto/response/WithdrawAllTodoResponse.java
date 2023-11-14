package com.pawith.todoapplication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawAllTodoResponse {
    private String teamProfileImage;
    private String categoryName;
    private String task;
}
