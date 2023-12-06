package com.pawith.commonmodule.cache.operators.impl;

import com.pawith.commonmodule.cache.operators.SetOperator;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

public class DefaultSetOperator<K> implements SetOperator<K> {
    private final ExpiringMap<K,K> localMap = ExpiringMap.builder()
        .variableExpiration()
        .build();

    @Override
    public void add(K k) {
        localMap.put(k,k);
    }

    @Override
    public void addWithExpire(K k, long expire, TimeUnit timeUnit) {
        localMap.put(k,k,ExpirationPolicy.CREATED,expire, TimeUnit.MINUTES);
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
