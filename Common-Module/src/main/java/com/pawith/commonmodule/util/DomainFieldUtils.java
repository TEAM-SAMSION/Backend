package com.pawith.commonmodule.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DomainFieldUtils {
    public static <T> T updateIfDifferent(T newValue, T currentValue) {
        return Objects.equals(newValue, currentValue) ? currentValue : Objects.requireNonNullElseGet(newValue, () -> currentValue);
    }

    public static class DomainValidateBuilder<T>{
        private T newValue;
        private T currentValue;
        private DomainValidateBuilder() {
        }

        private static <T> T validate(T newValue, T currentValue) {
            return Objects.equals(newValue, currentValue) ? currentValue : Objects.requireNonNullElseGet(newValue, () -> currentValue);
        }

        public static <T> DomainValidateBuilder<T> builder(Class<T> clazz){
            return new DomainValidateBuilder<T>();
        }

        public DomainValidateBuilder<T> newValue(T value){
            this.newValue = value;
            return this;
        }

        public DomainValidateBuilder<T> currentValue(T value){
            this.currentValue = value;
            return this;
        }

        public T validate(){
            return validate(newValue, currentValue);
        }
    }
}
