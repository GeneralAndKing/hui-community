package cn.hui_community.service.configuration;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Component;

/**
 * The configuration customize:
 * <ul>
 *     <li>{@link JwtDecoder}: It will be used in {@link JwtAuthenticationProvider}.</li>
 *     <li>{@link JwtEncoder}: It will be used to generate user JWT tokens.</li>
 * </ul>
 *
 * <p>
 * 2022/9/7 20:20:43
 * </p>
 *
 * @author yue
 * @see <a href="https://github.com/spring-projects/spring-security-samples/blob/main/servlet/spring-boot/java/jwt/login/src/main/java/example/RestConfig.java">Spring secuirty jwt demo</a>
 * @see <a href="https://docs.spring.io/spring-security/reference/6.0.0-M6/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-architecture">How JWT Authentication Works</a>
 * @see <a href="https://connect2id.com/products/nimbus-jose-jwt/examples/jwt-with-rsa-signature">JSON Web Token (JWT) with RSA signature</a>
 */
@Configuration
public class JwtConfiguration {
    @ConfigurationProperties(prefix = "jwt")
    @Data
    @Component
    static public class Properties {

        /**
         * User access token expires time. Default 12 hours.
         */
        private Integer accessTokenExpiresTime = 12;

        /**
         * User refresh token expires time. Default 7 days.
         */
        private Integer refreshTokenExpiresTime = 7;

        /**
         * User access token expires time unit. Default {@link ChronoUnit#HOURS}.
         */
        private ChronoUnit accessTokenExpiresUnit = ChronoUnit.HOURS;

        /**
         * User refresh token expires time unit. Default {@link ChronoUnit#DAYS}.
         */
        private ChronoUnit refreshTokenExpiresUnit = ChronoUnit.DAYS;

        /**
         * Jwt token issuer. Default application name.
         */
        private String issuer = "hui-community";

        /**
         * Jwt token audience.
         */
        private List<String> audience = Collections.emptyList();

        public Long refreshTokenExpiresTime() {
            return getRefreshTokenExpiresUnit().getDuration().toSeconds() * getRefreshTokenExpiresTime();
        }

    }


    private final RSAKey jwk;

    private final Properties jwtProperties;


    public JwtConfiguration(Properties jwtProperties) throws IOException, ParseException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        String content = new DefaultResourceLoader().getResource("classpath:key-store.json")
                .getContentAsString(StandardCharsets.UTF_8);
        jwk = RSAKey.parse(content);
        this.jwtProperties = jwtProperties;
    }

    /**
     * Decode user info from Jwt token.
     *
     * @return Jwt decoder
     * @throws JOSEException {@link RSAKey#toRSAPublicKey()} throws reason.
     */
    @Bean
    public JwtDecoder jwtDecoder() throws JOSEException {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(jwk.toRSAPublicKey()).build();
        // Add validators.
        OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
                new JwtTimestampValidator(Duration.ZERO),
                new JwtIssuerValidator(jwtProperties.getIssuer()));
        jwtDecoder.setJwtValidator(withClockSkew);
        return jwtDecoder;
    }

    /**
     * Encode user info to Jwt token.
     *
     * @return Encoder.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }


}