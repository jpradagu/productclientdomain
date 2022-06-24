package com.nttdata.bootcamp.registerproduct.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * CacheConfig.
 */
@Configuration
public class CacheConfig {

  /**
   * cacheConfigurarion.
   */
  @Bean
  public RedisCacheConfiguration cacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .disableCachingNullValues()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new GenericJackson2JsonRedisSerializer()));
  }

  /**
   * RedisCacheManagerBuilderCustomizer.
   */
  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return builder -> builder
        .withCacheConfiguration("customerWalletFindCache",
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
        .withCacheConfiguration("customeDebitCardFindCache",
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(8)));
  }
}
