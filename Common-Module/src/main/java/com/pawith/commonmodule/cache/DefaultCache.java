package com.pawith.commonmodule.cache;

import com.pawith.commonmodule.cache.operators.impl.ExpiredStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DefaultCache extends ExpiredStorage<Object, Object> implements Cache {
    private final String name;

    public void setTimeToLive(long timeToLive, TimeUnit timeUnit) {
        storage.setExpiration(timeToLive, timeUnit);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return storage;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = storage.get(key);
        return value == null ? null : () -> value;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T) storage.get(key);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        final Object value = storage.get(key);
        try {
            return value == null ? valueLoader.call() : (T) value;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        storage.put(key, value);
    }

    @Override
    public void evict(Object key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
