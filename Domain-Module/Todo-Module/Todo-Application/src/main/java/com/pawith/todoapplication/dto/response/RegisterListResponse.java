package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegisterListResponse {
    private final List<RegisterSimpleInfoResponse> registers;
}