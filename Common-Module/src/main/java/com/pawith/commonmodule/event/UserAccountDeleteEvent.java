package com.pawith.commonmodule.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAccountDeleteEvent {
    private final Long userId;
}
