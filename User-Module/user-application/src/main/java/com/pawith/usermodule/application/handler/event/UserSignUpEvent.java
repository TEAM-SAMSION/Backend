package com.pawith.usermodule.application.handler.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserSignUpEvent {
    private final String nickname;
    private final String email;
    private final String Provider;
}
