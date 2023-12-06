package com.pawith.commonmodule.cache.config;

import com.pawith.commonmodule.cache.CacheTemplate;
import com.pawith.commonmodule.cache.DefaultCacheTemplate;
import com.pawith.commonmodule.cache.operators.SetOperator;
import com.pawith.commonmodule.cache.operators.ValueOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheTemplate<?,?> cacheTemplate() {
        return new DefaultCacheTemplate<>();
    }

    @Bean
    public SetOperator<?> setOperator() {
        return cacheTemplate().opsForSet();
    }

    @Bean
    public ValueOperator<?,?> valueOperator() {
        return cacheTemplate().opsForValue();
    }
}
