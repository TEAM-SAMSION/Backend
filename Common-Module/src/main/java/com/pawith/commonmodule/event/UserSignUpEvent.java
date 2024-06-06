package com.pawith.commonmodule.event;

public record UserSignUpEvent(
    String nickname,
    String email
) {
    public static UserSignUpEvent of(String nickname, String email) {
        return new UserSignUpEvent(nickname, email);
    }
}
