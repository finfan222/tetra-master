package com.finfan.server.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author finfan
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customers", "products"); // Define cache names
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Bean
    public Caffeine caffeineSpec() {
        return Caffeine.newBuilder()
                .initialCapacity(10) // Initial capacity of the cache
                .maximumSize(100) // Maximum number of entries the cache can hold
                .expireAfterAccess(1, TimeUnit.HOURS); // Entries expire 1 hour after last access (read or write)
    }

}
