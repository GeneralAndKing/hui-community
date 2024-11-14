package cn.hui_community.service.configuration.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class GlobalAuthenticationDetailsSource
    implements AuthenticationDetailsSource<HttpServletRequest, GlobalWebAuthenticationDetails> {

  @Override
  public GlobalWebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new GlobalWebAuthenticationDetails(context);
  }

}
