package com.pawith.userapplication.handler.event;

import com.pawith.commonmodule.enums.Provider;
import lombok.*;

@Getter
public class UserSignUpEvent {
    private String nickname;
    private String email;
    private Provider provider;

    public UserSignUpEvent(String nickname, String email, Provider provider) {
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }
}
