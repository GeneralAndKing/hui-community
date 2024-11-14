package cn.hui_community.service.configuration.security.authentication;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class GlobalAuthenticationToken extends AbstractAuthenticationToken {

  private final Object principal;

  private final Object credentials;

  public GlobalAuthenticationToken(Object principal) {

    this(principal, null);
  }

  public GlobalAuthenticationToken(Object principal, Object credentials) {

    this(principal, credentials, null);
  }

  public GlobalAuthenticationToken(Object principal, Object credentials, Object details) {

    super(AuthorityUtils.NO_AUTHORITIES);
    this.principal = principal;
    this.credentials = credentials;
    setDetails(details);
  }

  public GlobalAuthenticationToken(
      Object principal, Object credentials, Object details, Collection<? extends GrantedAuthority> authorities
  ) {

    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    setDetails(details);
    setAuthenticated(true);
  }

  public static GlobalAuthenticationToken unauthenticated(Object principal) {

    return new GlobalAuthenticationToken(principal);
  }

  public static GlobalAuthenticationToken unauthenticated(Object principal, Object credentials) {

    return new GlobalAuthenticationToken(principal, credentials);
  }

  public static GlobalAuthenticationToken unauthenticated(Object principal, Object credentials, Object details) {

    return new GlobalAuthenticationToken(principal, credentials, details);
  }

  public static GlobalAuthenticationToken authenticated(Object principal) {

    return new GlobalAuthenticationToken(principal, null, null, AuthorityUtils.NO_AUTHORITIES);
  }

  public static GlobalAuthenticationToken authenticated(Object principal, Object credentials) {

    return new GlobalAuthenticationToken(principal, credentials, null, AuthorityUtils.NO_AUTHORITIES);
  }

  public static GlobalAuthenticationToken authenticated(Object principal, Object credentials, Object details) {

    return new GlobalAuthenticationToken(principal, credentials, details, AuthorityUtils.NO_AUTHORITIES);
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

}