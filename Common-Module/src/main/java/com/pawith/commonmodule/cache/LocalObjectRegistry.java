package com.pawith.commonmodule.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LocalObjectRegistry implements ObjectRegistry{
    private final Map<String, Object> registry = new ConcurrentHashMap<>();
    @Override
    public void register(String key, Object value) {
        registry.put(key, value);
    }

    @Override
    public Optional<Object> get(String key) {
        return Optional.of(registry.get(key));
    }

    @Override
    public Optional<String> getKeyFromValue(Object value) {
        return registry.entrySet().stream()
                .filter(entry -> entry.getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public boolean containsValue(Object value) {
        return registry.containsValue(value);
    }

    @Override
    public void remove(String key) {
        registry.remove(key);
    }

    @Override
    public void clear() {
        registry.clear();
    }
}
