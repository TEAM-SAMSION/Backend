package com.pawith.commonmodule.observer.observer;

import com.pawith.commonmodule.observer.subject.Status;

@FunctionalInterface
public interface Observer<T extends Status, R> {
    R accept(T status);
}
