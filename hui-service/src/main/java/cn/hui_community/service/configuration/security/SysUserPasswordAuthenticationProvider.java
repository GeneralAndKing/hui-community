package cn.hui_community.service.configuration.security;

import cn.hui_community.service.model.SysPermission;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SysUserPasswordAuthenticationProvider implements AuthenticationProvider {
    private final SysUserRepository sysUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SysUserPasswordAuthenticationToken unauthenticated = (SysUserPasswordAuthenticationToken) authentication;
        SysUser sysUser = sysUserRepository.findByUsername(unauthenticated.getUsername())
                .orElseThrow(() -> new BadCredentialsException("can't found username"));
        if (!passwordEncoder.matches(unauthenticated.getPassword(), sysUser.getPassword())) {
            throw new BadCredentialsException("wrong password");
        }
        List<SysPermission> sysPermissions = sysUser.getRoles().stream().flatMap(sysRole -> sysRole.getPermissions().stream()).distinct().toList();
        return SysUserPasswordAuthenticationToken.authenticated(unauthenticated.getUsername(), unauthenticated.getPassword(), sysPermissions);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SysUserPasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }


}
