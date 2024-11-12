package cn.hui_community.service.configuration.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

@Getter
public class SysUserPasswordAuthenticationToken extends AbstractAuthenticationToken {


    private final String username;
    private final String password;

    private SysUserPasswordAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        setAuthenticated(false);
    }

    private SysUserPasswordAuthenticationToken(String username, String password,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.password = password;
        setAuthenticated(true);
    }

    public static SysUserPasswordAuthenticationToken unauthenticated(String username, String password) {
        return new SysUserPasswordAuthenticationToken(username, password);
    }

    public static SysUserPasswordAuthenticationToken authenticated(String username, String password,
                                                                   Collection<? extends GrantedAuthority> authorities) {
        return new SysUserPasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
}
