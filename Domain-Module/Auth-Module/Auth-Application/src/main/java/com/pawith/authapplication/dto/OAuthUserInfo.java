package com.pawith.authapplication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthUserInfo {
    private final String username;
    private final String email; // email -> sub, 사용자 식별 정보를 email에서 sub로 변경
    private final String sub;
}
