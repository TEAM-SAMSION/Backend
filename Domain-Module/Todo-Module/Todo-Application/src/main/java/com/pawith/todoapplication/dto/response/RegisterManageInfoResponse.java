package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterManageInfoResponse {
    private final Long registerId;
    private final Authority authority;
    private final String registerName;
    private final String registerEmail;
    private final String profileImage;
}
