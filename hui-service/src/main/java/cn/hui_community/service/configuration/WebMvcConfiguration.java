package cn.hui_community.service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
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
