package com.pawith.commonmodule.cache.operators;

import java.util.concurrent.TimeUnit;

public interface SetOperator<K> {

    void add(K k);

    void addWithExpire(K k, long expire, TimeUnit timeUnit);

    void remove(K k);

    boolean contains(K k);
}
