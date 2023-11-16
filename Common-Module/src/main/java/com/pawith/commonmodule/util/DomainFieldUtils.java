package com.pawith.commonmodule.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DomainFieldUtils {
    public static <T> T updateIfDifferent(T newValue, T currentValue) {
        return newValue.equals(currentValue) ? currentValue : newValue;
    }
}
