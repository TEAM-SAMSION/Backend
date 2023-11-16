package com.pawith.commonmodule.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> {

    private final List<T> content;

    @Builder
    private ListResponse(List<T> content) {
        this.content = content;
    }

    public static <T> ListResponse<T> from(List<T> content) {
        return new ListResponse<>(content);
    }
}
