package cn.hui_community.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HuiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuiServiceApplication.class, args);
    }

}
