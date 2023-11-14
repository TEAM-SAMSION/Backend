package com.pawith.todoapplication.handler.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryDeleteEvent {
    private final Long categoryId;
}
