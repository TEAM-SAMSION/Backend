package com.pawith.commonmodule.util;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceUtils {

    public static <T> Slice<T> getSliceImpl(List<T> list, Pageable pageable) {
        boolean hasNext = false;
        if (list.size() > pageable.getPageSize()) {
            hasNext = true;
            list.remove(list.size() - 1);
        }

        return new SliceImpl<>(list, pageable, hasNext);
    }
}
