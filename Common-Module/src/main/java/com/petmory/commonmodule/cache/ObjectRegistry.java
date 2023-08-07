package com.petmory.commonmodule.cache;

import java.util.Optional;

public interface ObjectRegistry {
    void register(String key, Object value);
    Optional<Object> get(String key);

    Optional<String> getKeyFromValue(Object value);

    boolean containsValue(Object value);

    void remove(String key);
    void clear();
}
