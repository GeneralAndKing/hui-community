package cn.hui_community.service.service.impl;

import cn.hui_community.service.configuration.JwtConfiguration;
import cn.hui_community.service.configuration.security.Token;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SysUserRepository sysUserRepository;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtConfiguration.Properties jwtProperties;
    private final ObjectMapper objectMapper;

    @Override
    public Token buildTokenFromSysUser(SysUser user) {
        Instant now = Instant.now();
        Token token = generateNewUserToken(user, now);
        return token;
//    TODO：缓存用户 token 以及 SSO
//    Optional<UserToken> existTokenOptional = userTokenRepository.findById(user.getName());
//    Boolean onlyOnceOrNoCache = getBoolean(AUTHENTICATION_ONCE) || existTokenOptional.isEmpty();
//    if (Boolean.TRUE.equals(onlyOnceOrNoCache)) {
//      userTokenRepository.deleteById(user.getName());
//      userTokenRepository.save(userToken);
//      return userToken;
//    }
//    UserToken existToken = existTokenOptional.get();
//    Instant expiresAt = jwtDecoder.decode(existToken.getRefreshToken()).getExpiresAt();
//    if (Objects.isNull(expiresAt) || expiresAt.isBefore(now)) {
//      userTokenRepository.deleteById(user.getName());
//      userTokenRepository.save(userToken);
//      return userToken;
//    }
//    return existToken;
    }

    private Token generateNewUserToken(SysUser user, Instant now) {
        return new Token()
                .setAccessToken(buildAccessToken(user, now))
                .setRefreshToken(buildRefreshToken(user, now))
                .setId(user.getId())
                .setSubject("SYS")
                .setUsername(user.getUsername());
    }

    private String buildRefreshToken(SysUser user, Instant now) {
        return buildToken(user, now,
                now.plus(jwtProperties.getRefreshTokenExpiresTime(), jwtProperties.getRefreshTokenExpiresUnit()),
                claim -> claim.putAll(Collections.emptyMap())
        );
    }

    private String buildAccessToken(SysUser user, Instant now) {
        return buildToken(user, now,
                now.plus(jwtProperties.getAccessTokenExpiresTime(), jwtProperties.getAccessTokenExpiresUnit()),
//      TODO: 需要添加用户角色进去
                claim -> claim.putAll(user.toTokenInfo())
        );
    }

    private String buildToken(SysUser user, Instant now, Instant expires, Consumer<Map<String, Object>> claimsConsumer) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expires)
                .notBefore(now)
                .id(user.getId())
                .claims(claimsConsumer)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}
