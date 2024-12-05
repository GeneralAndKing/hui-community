package cn.hui_community.service.configuration;

import cn.hui_community.service.model.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.List;

@Configuration
public class RedisConfiguration {

    public static class TokenKeyToByteArrayConverter implements Converter<Token.Key, byte[]> {

        @Override
        public byte[] convert(Token.Key key) {
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
}
