package cn.hui_community.service.configuration.security;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.util.IOUtils;
import com.nimbusds.jose.util.X509CertUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.time.Duration;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.util.StreamUtils;

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

  private final RSAKey jwk;
  private final JwtProperties jwtProperties;

  public JwtConfiguration(JwtProperties jwtProperties) throws IOException, ParseException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
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