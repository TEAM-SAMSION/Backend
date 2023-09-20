package com.pawith.todoapplication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TodoHomeResponse {

    private Long todoId;
    private String task;
    private String status;

}
