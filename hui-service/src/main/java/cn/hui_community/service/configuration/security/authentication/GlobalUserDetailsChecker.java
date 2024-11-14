package cn.hui_community.service.configuration.security.authentication;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GlobalUserDetailsChecker implements UserDetailsChecker {

  @Override
  public void check(UserDetails user) {
    Assert.isTrue(user.isAccountNonLocked(), () -> {
      throw new LockedException("账户已锁定");
    });

    Assert.isTrue(user.isEnabled(), () -> {
      throw new DisabledException("账户已禁用");
    });

    Assert.isTrue(user.isAccountNonExpired(), () -> {
      throw new AccountExpiredException("账户已过期");
    });

    Assert.isTrue(user.isCredentialsNonExpired(), () -> {
      throw new CredentialsExpiredException("账户认证已过期");
    });

  }

}