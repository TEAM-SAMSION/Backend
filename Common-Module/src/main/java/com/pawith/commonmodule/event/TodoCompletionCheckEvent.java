package com.pawith.commonmodule.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoCompletionCheckEvent {
    private final Long todoId;
}
