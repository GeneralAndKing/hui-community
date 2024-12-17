package cn.hui_community.service.configuration;

import cn.hui_community.service.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfiguration {

    public static class TokenKeyToByteArrayConverter implements Converter<Token.Key, byte[]> {

        @Override
        public byte[] convert(@NonNull Token.Key key) {
            return key.toString().getBytes();

        }
    }

    public static class TokenKeyToStringConverter implements Converter<Token.Key, String> {

        @Override
        public String convert(Token.Key key) {
            return key.toString();

        }
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(List.of(new TokenKeyToByteArrayConverter(), new TokenKeyToStringConverter()));
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /**
     * Configures and returns a CacheManager to manage Redis caches.
     *
     * @param factory the Redis connection factory
     * @param universalTtl the default time-to-live for cache entries
     * @return a RedisCacheManager configured with the specified settings
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory,
            @Value("${cache.universal-ttl}") Duration universalTtl) {
        // Set default cache configuration
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(universalTtl); // Set entry time-to-live

        // Build and return the RedisCacheManager
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }

}
