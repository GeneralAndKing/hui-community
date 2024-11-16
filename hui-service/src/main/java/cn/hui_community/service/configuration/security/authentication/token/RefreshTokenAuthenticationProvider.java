package cn.hui_community.service.configuration.security.authentication.token;

import cn.hui_community.service.enums.SubjectEnum;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;


@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final JwtDecoder jwtDecoder;
    private final SysUserRepository sysUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RefreshTokenAuthenticationToken refreshTokenAuthenticationToken = (RefreshTokenAuthenticationToken) authentication;
        String id = refreshTokenAuthenticationToken.getPrincipal().toString();
        String refreshToken = (String) refreshTokenAuthenticationToken.getCredentials();
        Jwt jwt = jwtDecoder.decode(refreshToken);
        if (!jwt.getClaims().getOrDefault("id", "").equals(id)) {
            throw new BadJwtException("token does not match user.");
        }
        String subject = jwt.getClaims().getOrDefault("subject", "").toString();
        switch (SubjectEnum.valueOf(subject)){
            case SYS -> {
                SysUser sysUser = sysUserRepository.findById(id)
                        .orElseThrow(() -> new BadCredentialsException("can't found sys user."));
                return RefreshTokenAuthenticationToken.authenticated(sysUser, sysUser.getPassword(), sysUser.generateAuthorities());
            }
        }

        throw new BadCredentialsException("can't found %s subject.".formatted(subject));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
