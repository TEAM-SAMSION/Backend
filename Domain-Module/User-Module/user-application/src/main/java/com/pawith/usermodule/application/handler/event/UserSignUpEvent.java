package com.pawith.usermodule.application.handler.event;

import lombok.*;

@Getter
@Builder
public class UserSignUpEvent {
    private String nickname;
    private String email;
    private String provider;
}
