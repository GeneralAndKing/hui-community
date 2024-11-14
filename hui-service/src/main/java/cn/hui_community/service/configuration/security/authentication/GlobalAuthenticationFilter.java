package cn.hui_community.service.configuration.security.authentication;

import cn.hui_community.service.service.AuthenticationService;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public abstract class GlobalAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final HttpMethod method;

  public GlobalAuthenticationFilter(String pattern, HttpMethod method) {
    super(new AntPathRequestMatcher(pattern, method.name()), SpringUtil.getBean(AuthenticationManager.class));
    setAuthenticationDetailsSource(new GlobalAuthenticationDetailsSource());
    setAuthenticationSuccessHandler(new GlobalAuthenticationSuccessHandler(SpringUtil.getBean(AuthenticationService.class)));
    setAuthenticationFailureHandler(new GlobalAuthenticationFailureHandler());
    this.method = method;
  }

  public GlobalAuthenticationFilter(
      String pattern, HttpMethod method,
      AuthenticationDetailsSource<HttpServletRequest, GlobalWebAuthenticationDetails> detailsSource
  ) {
    this(pattern, method);
    setAuthenticationDetailsSource(detailsSource);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {

    if (!method.matches(request.getMethod())) {
      throw new AuthenticationServiceException("登陆请求协议不支持");
    }

    GlobalAuthenticationToken authentication = combinationAuthentication(request);

    return getAuthenticationManager().authenticate(authentication);
  }

  public abstract GlobalAuthenticationToken combinationAuthentication(HttpServletRequest request) throws IOException;

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
  }
}
