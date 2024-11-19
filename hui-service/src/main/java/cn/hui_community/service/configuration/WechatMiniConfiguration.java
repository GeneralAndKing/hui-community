package cn.hui_community.service.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class WechatMiniConfiguration {
    @Component
    @Data
    @ConfigurationProperties(prefix = "wechat-mini")
    public static class Properties {
        @Data
        static public class Pair {
            private String appId;

            private String secret;
        }

        private Pair user;
        private Pair shopkeeper;
    }


}
