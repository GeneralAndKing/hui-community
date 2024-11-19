package cn.hui_community.service.configuration;

import cn.hui_community.service.configuration.security.GlobalAuthHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

    private final Map<String, AbstractAuthenticationProcessingFilter> filterMap;





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, GlobalAuthHandler globalAuthHandler, AuthenticationManager authenticationManager) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/sys/login", "/refresh-token","/user/login").permitAll()
                        .requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth2 -> oauth2
                        .accessDeniedHandler(globalAuthHandler)
                        .authenticationEntryPoint(globalAuthHandler)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter()))
                );

        filterMap.forEach((key, filter) -> {
            log.info("configuration filter: %s".formatted(key));
            filter.setAuthenticationSuccessHandler(globalAuthHandler);
            filter.setAuthenticationFailureHandler(globalAuthHandler);
            filter.setAuthenticationManager(authenticationManager);
            httpSecurity.addFilterAfter(filter, BearerTokenAuthenticationFilter.class);
        });
        return httpSecurity.build();
    }

    /**
     * Converter jwt scope to user current roles.
     *
     * @return converter
     */
    private JwtAuthenticationConverter authenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
