package com.pawith.userapplication.dto.response;

import lombok.Getter;

@Getter
public class UserInfoResponse {
    private final String nickname;
    private final String email;
    private final String profileImageUrl;

    public UserInfoResponse(String nickname, String email, String profileImageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }
}
