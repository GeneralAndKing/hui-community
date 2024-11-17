package cn.hui_community.service.configuration.security.authentication.password;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

@Getter
public class SysUserPasswordAuthenticationToken extends AbstractAuthenticationToken {


    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private Object credentials;

    public SysUserPasswordAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public SysUserPasswordAuthenticationToken(Object principal, Object credentials,
                                              Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public static SysUserPasswordAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new SysUserPasswordAuthenticationToken(principal, credentials);
    }

    public static SysUserPasswordAuthenticationToken authenticated(Object principal, Object credentials,
                                                                   Collection<? extends GrantedAuthority> authorities) {
        return new SysUserPasswordAuthenticationToken(principal, credentials, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
