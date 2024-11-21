package cn.hui_community.service.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfiguration  implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/sys-api",
                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.sys"))
                .addPathPrefix("/user-api",
                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.user"))
                .addPathPrefix("/shopkeeper-api",
                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.shopkeeper"));
    }
}
