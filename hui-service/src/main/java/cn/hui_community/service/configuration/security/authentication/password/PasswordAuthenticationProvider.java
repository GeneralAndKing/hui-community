package cn.hui_community.service.configuration.security.authentication.password;

import cn.hui_community.service.configuration.security.authentication.GlobalAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PasswordAuthenticationProvider extends GlobalAuthenticationProvider<PasswordAuthenticationToken> {

  @Override
  public String validate4Username(PasswordAuthenticationToken authentication) {
    String username = authentication.getUsername();
    return username;
  }
}
