package com.pawith.commonmodule.cache.operators.impl;

import com.pawith.commonmodule.cache.operators.ValueOperator;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

public class DefaultValueOperator<K,V> implements ValueOperator<K,V> {
    private final ExpiringMap<K,V> localMap = ExpiringMap.builder()
        .variableExpiration()
        .build();
    @Override
    public void set(K k, V v) {
        localMap.put(k,v);
    }

    @Override
    public void setWithExpire(K k, V v, long expire, TimeUnit timeUnit) {
        localMap.put(k,v, ExpirationPolicy.CREATED,expire,timeUnit);
    }

    @Override
    public V get(K k) {
        return localMap.get(k);
    }

    @Override
    public void remove(K k) {
        localMap.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return localMap.containsKey(k);
    }
}
