package cn.hui_community.service.configuration.security.authentication.password;

import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@RequiredArgsConstructor
public class SysUserPasswordAuthenticationProvider implements AuthenticationProvider {
    private final SysUserRepository sysUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SysUserPasswordAuthenticationToken sysUserPasswordAuthenticationToken = (SysUserPasswordAuthenticationToken) authentication;
        String username = sysUserPasswordAuthenticationToken.getPrincipal().toString();
        String password = sysUserPasswordAuthenticationToken.getCredentials().toString();
        SysUser sysUser = sysUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("can't found %s sys user.".formatted(username)));
        //TODO auth
        if (passwordEncoder.matches(password, sysUser.getPassword())) {
            return SysUserPasswordAuthenticationToken.authenticated(sysUser, sysUser.getPassword(), sysUser.generateAuthorities());
        } else {
            throw new BadCredentialsException("%s sys user password is incorrect.".formatted(username));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SysUserPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
