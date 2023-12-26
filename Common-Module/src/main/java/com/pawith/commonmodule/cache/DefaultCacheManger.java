package com.pawith.commonmodule.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class DefaultCacheManger implements CacheManager {
    private static final Map<String, DefaultCache> caches = new ConcurrentHashMap<>();

    public void setupTimeToLive(long timeToLive, TimeUnit timeUnit) {
        caches.values().forEach(cache -> cache.setTimeToLive(timeToLive, timeUnit));
    }

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, DefaultCache::new);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }
}
