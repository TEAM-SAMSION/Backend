package com.pawith.commonmodule.observer.subject;

import com.pawith.commonmodule.observer.observer.Observer;

public interface Subject<T,R> {
    void registerObserver(Observer<? extends Status, ?> o);
    void removeObserver(Observer<? extends Status, ?> o);
    R notifyObservers(T object);
}