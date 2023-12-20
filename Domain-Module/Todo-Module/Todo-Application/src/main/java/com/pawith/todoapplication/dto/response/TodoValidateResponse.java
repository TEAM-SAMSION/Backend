package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoValidateResponse {
    private boolean isNotValidate;
}
