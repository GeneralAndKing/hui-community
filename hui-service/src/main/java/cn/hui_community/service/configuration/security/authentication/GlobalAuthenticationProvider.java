package cn.hui_community.service.configuration.security.authentication;

import jakarta.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;

@SuppressWarnings("unchecked")
public abstract class GlobalAuthenticationProvider<AuthenticationToken extends Authentication>
    implements AuthenticationProvider {

  private final Class<AuthenticationToken> clazz;
  @Resource
  private UserDetailsService userDetailsService;
  @Resource
  private UserDetailsChecker userDetailsChecker;

  {
    Type superClass = getClass().getGenericSuperclass();
    if (superClass instanceof ParameterizedType) {
      this.clazz = (Class<AuthenticationToken>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
    } else {
      throw new IllegalArgumentException("泛型类型未找到");
    }
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = validate4Username((AuthenticationToken) authentication);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    userDetailsChecker.check(userDetails);
    GlobalAuthenticationToken token = GlobalAuthenticationToken.authenticated(userDetails, null, authentication.getDetails());
    token.setDetails(authentication.getDetails());
    return token;
  }

  public abstract String validate4Username(AuthenticationToken authentication);

  @Override
  public boolean supports(Class<?> authentication) {
    return clazz.isAssignableFrom(authentication);
  }

}