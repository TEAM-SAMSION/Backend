package com.pawith.usermodule.application.handler.event;

import lombok.*;

@Getter
@Builder
public class UserSignUpEvent {
    private String nickname;
    private String email;
    private String provider;

    public UserSignUpEvent(String nickname, String email, String provider) {
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }
}
