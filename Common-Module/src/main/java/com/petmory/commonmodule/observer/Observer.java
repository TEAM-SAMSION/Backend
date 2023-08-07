package com.petmory.commonmodule.observer;

import com.petmory.commonmodule.subject.Status;

@FunctionalInterface
public interface Observer<T extends Status, R> {
    R accept(T status);
}
