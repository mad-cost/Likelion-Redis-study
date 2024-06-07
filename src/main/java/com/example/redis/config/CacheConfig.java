package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
// @EnableCaching을 사용하면 캐시를 관리하는 CacheManger의 구현체가 Bean으로 등록되어야 한다
@EnableCaching
public class CacheConfig {
  @Bean
  public RedisCacheManager cacheManager(
          RedisConnectionFactory redisConnectionFactory
  ) {
    RedisCacheConfiguration configuration = RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            // Time To Live: 만료 시간
            .entryTtl(Duration.ofSeconds(60))
            // Key 접두사 설정
            .computePrefixWith(CacheKeyPrefix.simple())
            // Value 직렬화 / 역직렬화 방법
            .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json())
            );

    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
    RedisCacheConfiguration itemAllConfig = RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()
            .entryTtl(Duration.ofSeconds(10))
            .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())
            );
    configMap.put("itemAllCache", itemAllConfig);

    // 실제 매니저를 등록하는 과정
    return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(configuration)
//                .withCacheConfiguration("itemAllCache", itemAllConfig)
            .withInitialCacheConfigurations(configMap)
            .build();
  }
}