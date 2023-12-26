package com.pawith.commonmodule.cache.operators.impl;

import com.pawith.commonmodule.cache.operators.ValueOperator;
import net.jodah.expiringmap.ExpirationPolicy;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class DefaultValueOperator<K,V> extends ExpiredStorage<K,V> implements ValueOperator<K,V> {
    @Override
    public void set(K k, V v) {
        storage.put(k,v);
    }

    @Override
    public void setWithExpire(K k, V v, long expire, TimeUnit timeUnit) {
        storage.put(k,v, ExpirationPolicy.CREATED,expire,timeUnit);
    }

    @Override
    public V get(K k) {
        return storage.get(k);
    }

    @Override
    public Collection<K> getKeys() {
        return storage.keySet();
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
