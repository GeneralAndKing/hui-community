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

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfiguration  {
//    @Override
//    protected void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.addPathPrefix("/admin",
//                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.admin"))
//                .addPathPrefix("/user",
//                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.user"))
//                .addPathPrefix("/shopkeeper",
//                        clazz -> clazz.getPackageName().startsWith("cn.hui_community.service.controller.shopkeeper"));
//    }


}
