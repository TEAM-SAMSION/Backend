package com.pawith.commonmodule.cache.operators;

import java.util.concurrent.TimeUnit;

public interface ValueOperator<K,V> {

    void set(K k, V v);

    void setWithExpire(K k, V v, long expire, TimeUnit timeUnit);

    V get(K k);

    void remove(K k);

    boolean contains(K k);
}
