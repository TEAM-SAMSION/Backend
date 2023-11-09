package com.pawith.commonmodule.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ListResponse<T> {

    private final List<T> content;

    @Builder
    public ListResponse(List<T> content) {
        this.content = content;
    }

    public static <T> ListResponse<T> from(List<T> content) {
        return new ListResponse<>(content);
    }
}
