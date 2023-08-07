package com.petmory.commonmodule.observer.observer;

import com.petmory.commonmodule.observer.subject.Status;

@FunctionalInterface
public interface Observer<T extends Status, R> {
    R accept(T status);
}
