package com.pawith.commonmodule.event;

import com.pawith.commonmodule.enums.Provider;

public record UserSignUpEvent(
    String nickname,
    String email,
    Provider provider
) {
}
