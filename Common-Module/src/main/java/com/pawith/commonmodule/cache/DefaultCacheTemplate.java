package com.pawith.commonmodule.cache;

import com.pawith.commonmodule.cache.operators.SetOperator;
import com.pawith.commonmodule.cache.operators.ValueOperator;
import com.pawith.commonmodule.cache.operators.impl.DefaultSetOperator;
import com.pawith.commonmodule.cache.operators.impl.DefaultValueOperator;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultCacheTemplate<K,V> implements CacheTemplate<K,V> {

    private final SetOperator<K> SET_OPERATOR = new DefaultSetOperator<>();
    private final ValueOperator<K,V> VALUE_OPERATOR = new DefaultValueOperator<>();

    @Override
    public SetOperator<K> opsForSet() {
        return SET_OPERATOR;
    }

    @Override
    public ValueOperator<K,V> opsForValue() {
        return VALUE_OPERATOR;
    }
}
