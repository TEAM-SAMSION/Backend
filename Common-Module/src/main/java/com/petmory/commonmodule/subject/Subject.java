package com.petmory.commonmodule.subject;

import com.petmory.commonmodule.observer.Observer;

public interface Subject<T,R> {
    void registerObserver(Observer<? extends Status, ?> o);
    void removeObserver(Observer<? extends Status, ?> o);
    R notifyObservers(T object);
}