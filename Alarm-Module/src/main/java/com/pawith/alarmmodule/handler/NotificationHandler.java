package com.pawith.alarmmodule.handler;

import java.util.Collection;

public interface NotificationHandler<T> {
    void send(T t);

    void sendMultiAsync(Collection<T> t);

    void sendAsync(T t);
}
