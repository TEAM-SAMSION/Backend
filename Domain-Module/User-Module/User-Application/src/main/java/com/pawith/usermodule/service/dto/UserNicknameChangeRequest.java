package com.pawith.usermodule.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNicknameChangeRequest {
    private String nickname;

    public UserNicknameChangeRequest(String nickname) {
        this.nickname = nickname;
    }
}
