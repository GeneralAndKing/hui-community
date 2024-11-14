package cn.hui_community.service.configuration.security.authentication.password;

import cn.hui_community.service.configuration.security.authentication.GlobalAuthenticationToken;
import lombok.Getter;

@Getter
public class PasswordAuthenticationToken extends GlobalAuthenticationToken {

  private final String username;

  private final String password;

  public PasswordAuthenticationToken(String username, String password, Object details) {
    super(username, password, details);
    this.username = username;
    this.password = password;
  }

}
