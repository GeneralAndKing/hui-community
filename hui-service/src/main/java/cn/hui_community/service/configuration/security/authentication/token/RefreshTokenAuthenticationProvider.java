package cn.hui_community.service.configuration.security.authentication.token;

import cn.hui_community.service.enums.SubjectEnum;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.repository.UserRepository;
import cn.hui_community.service.component.TokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@RequiredArgsConstructor
@Component
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;
    private final SysUserRepository sysUserRepository;
    private final UserRepository userRepository;
    private final TokenFactory tokenFactory;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RefreshTokenAuthenticationToken refreshTokenAuthenticationToken = (RefreshTokenAuthenticationToken) authentication;
        String id = refreshTokenAuthenticationToken.getPrincipal().toString();
        String refreshToken = (String) refreshTokenAuthenticationToken.getCredentials();
        Jwt jwt = jwtDecoder.decode(refreshToken);
        if (!jwt.getClaims().getOrDefault("id", "").equals(id)) {
            throw new BadJwtException("token does not match user.");
        }
        if (!tokenFactory.validateRefreshToken(id, refreshToken)) {
            throw new BadCredentialsException("invalid refresh token.");
        }
        String subject = jwt.getClaims().getOrDefault("subject", "").toString();
        switch (SubjectEnum.valueOf(subject)) {
            case SYS -> {
                SysUser sysUser = sysUserRepository.findById(id)
                        .orElseThrow(() -> new BadCredentialsException("can't found sys user."));
                return RefreshTokenAuthenticationToken.authenticated(sysUser, sysUser.getPassword(), sysUser.generateAuthorities());
            }
            case USER -> {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new BadCredentialsException("can't found user."));
                return RefreshTokenAuthenticationToken.authenticated(user, null, Collections.emptySet());
            }
        }

        throw new BadCredentialsException("can't found %s subject.".formatted(subject));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
