package cn.hui_community.service.configuration;

import cn.hui_community.service.configuration.security.JsonBodyAuthHandler;
import cn.hui_community.service.configuration.security.authentication.password.SysUserPasswordAuthenticationFilter;
import cn.hui_community.service.configuration.security.authentication.password.SysUserPasswordAuthenticationProvider;
import cn.hui_community.service.configuration.security.authentication.token.RefreshTokenAuthenticationFilter;
import cn.hui_community.service.configuration.security.authentication.token.RefreshTokenAuthenticationProvider;
import cn.hui_community.service.repository.SysUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtDecoder jwtDecoder;
    private final SysUserRepository sysUserRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new RefreshTokenAuthenticationProvider(jwtDecoder, sysUserRepository))
                .authenticationProvider(new SysUserPasswordAuthenticationProvider(sysUserRepository, passwordEncoder()))
                .parentAuthenticationManager(null);
        return authenticationManagerBuilder.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JsonBodyAuthHandler jsonBodyAuthHandler, AuthenticationManager authenticationManager) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/sys/login","/refresh-token").permitAll()
                        .requestMatchers("/swagger-ui", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(new SysUserPasswordAuthenticationFilter(objectMapper, authenticationManager, jsonBodyAuthHandler), BearerTokenAuthenticationFilter.class)
                .addFilterAfter(new RefreshTokenAuthenticationFilter(objectMapper, authenticationManager, jsonBodyAuthHandler), BearerTokenAuthenticationFilter.class)

                .oauth2ResourceServer(oauth2 -> oauth2
                        .accessDeniedHandler(jsonBodyAuthHandler)
                        .authenticationEntryPoint(jsonBodyAuthHandler)
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter()))
                )
                .build();
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
