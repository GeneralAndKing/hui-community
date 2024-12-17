package cn.hui_community.service.configuration;

import cn.hui_community.service.configuration.security.GlobalAuthHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
    /**
     * Configuration for spring security, including cors, authentication and authorization.
     * <ul>
     *     <li>{@link #passwordEncoder()}</li>
     *     <li>{@link #authManager(HttpSecurity, Map)}</li>
     *     <li>{@link #securityFilterChain(HttpSecurity, GlobalAuthHandler, Map)}</li>
     *     <li>{@link #corsConfigurationSource()}</li>
     * </ul>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, Map<String, AuthenticationProvider> providerMap) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        providerMap.forEach((key, provider) -> {
            log.info("Configuring provider for key: {}", key);
            authenticationManagerBuilder.authenticationProvider(provider);
        });
        return authenticationManagerBuilder.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, GlobalAuthHandler globalAuthHandler, Map<String, AbstractAuthenticationProcessingFilter> filterMap) throws Exception {
        HttpSecurity httpSecurity = http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/sys-api/login", "/refresh-token", "/user-api/login").permitAll()
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
