package com.pawith.commonmodule.slice;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
public final class SliceResponse<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final boolean hasNext;

    @Builder
    private SliceResponse(List<T> content, int page, int size, boolean hasNext) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.hasNext = hasNext;
    }

    public static <T> SliceResponse<T> from(Slice<T> slice){
        return SliceResponse.<T>builder()
            .content(slice.getContent())
            .page(slice.getNumber())
            .size(slice.getSize())
            .hasNext(slice.hasNext())
            .build();
    }
}
