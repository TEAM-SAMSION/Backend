package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterSimpleInfoResponse {
    private final Long registerId;
    private final String registerName;
}
