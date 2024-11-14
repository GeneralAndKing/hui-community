package cn.hui_community.service.configuration.security;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("jwt")
public class JwtProperties {

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
  private String issuer = "lever";

  /**
   * Jwt token audience.
   */
  private List<String> audience = Collections.emptyList();

}
