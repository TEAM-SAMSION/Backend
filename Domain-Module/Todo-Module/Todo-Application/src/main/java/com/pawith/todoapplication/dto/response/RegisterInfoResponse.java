package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterInfoResponse {
    private final Long registerId;
    private final String registerName;
}
