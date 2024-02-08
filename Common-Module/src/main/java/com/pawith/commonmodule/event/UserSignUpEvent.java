package com.pawith.commonmodule.event;

import com.pawith.commonmodule.enums.Provider;

public record UserSignUpEvent(
    String nickname,
    String email,
    Provider provider
) {
    public static UserSignUpEvent of(String nickname, String email, Provider provider) {
        return new UserSignUpEvent(nickname, email, provider);
    }
}
