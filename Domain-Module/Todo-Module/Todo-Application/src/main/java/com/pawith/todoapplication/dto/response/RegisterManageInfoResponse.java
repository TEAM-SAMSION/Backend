package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterManageInfoResponse {
    private final Long registerId;
    private final String authority;
    private final String registerName;
    private final String registerEmail;
    private final String profileImage;
}
