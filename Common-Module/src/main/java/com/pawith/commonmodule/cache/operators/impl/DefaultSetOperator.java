package com.pawith.commonmodule.cache.operators.impl;

import com.pawith.commonmodule.cache.operators.SetOperator;
import net.jodah.expiringmap.ExpirationPolicy;

import java.util.concurrent.TimeUnit;

public class DefaultSetOperator<K> extends ExpiredStorage<K,K> implements SetOperator<K> {
    @Override
    public void add(K k) {
        storage.put(k,k);
    }

    @Override
    public void addWithExpire(K k, long expire, TimeUnit timeUnit) {
        storage.put(k,k,ExpirationPolicy.CREATED,expire, TimeUnit.MINUTES);
    }

    @Override
    public void remove(K k) {
        storage.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return storage.containsKey(k);
    }
}
