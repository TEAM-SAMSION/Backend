package com.pawith.commonmodule.cache;

import com.pawith.commonmodule.cache.operators.SetOperator;
import com.pawith.commonmodule.cache.operators.ValueOperator;

public interface CacheTemplate<K,V> {
    SetOperator<K> opsForSet();
    ValueOperator<K,V> opsForValue();
}
