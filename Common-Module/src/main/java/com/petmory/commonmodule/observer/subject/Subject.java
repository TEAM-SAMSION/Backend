package com.petmory.commonmodule.observer.subject;

import com.petmory.commonmodule.observer.observer.Observer;

public interface Subject<T,R> {
    void registerObserver(Observer<? extends Status, ?> o);
    void removeObserver(Observer<? extends Status, ?> o);
    R notifyObservers(T object);
}