package cn.hui_community.service.component;

import cn.hui_community.service.configuration.JwtConfiguration;
import cn.hui_community.service.enums.PermissionTypeEnum;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.Token;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TokenFactory {


    private final JwtEncoder jwtEncoder;
    private final JwtConfiguration.Properties jwtProperties;
    private final TokenRepository tokenRepository;

    @Transactional(rollbackFor = Exception.class)
    public Token buildFromSysUser(SysUser user) {
        String subject = PermissionTypeEnum.SYS.getValue();
        Instant now = Instant.now();
        Token token = Token.builder()
                .accessToken(buildAccessToken(user.getId(), subject, now, user.toTokenInfo()))
                .refreshToken(buildRefreshToken(user.getId(), subject, now))
                .key(Token.Key.builder().id(user.getId())
                        .subject(subject)
                        .build())
                .timeToLive(jwtProperties.refreshTokenExpiresTime())
                .username(user.getUsername()).build();
        tokenRepository.save(token);
        return token;
    }

    @Transactional(rollbackFor = Exception.class)
    public Token buildFromUser(User user) {
        String subject = PermissionTypeEnum.USER.getValue();
        Instant now = Instant.now();
        Token token = Token.builder()
                .accessToken(buildAccessToken(user.getId(), subject, now, Map.of()))
                .refreshToken(buildRefreshToken(user.getId(), subject, now))
                .key(Token.Key.builder().id(user.getId())
                        .subject(subject)
                        .build())
                .username(user.getName())
                .timeToLive(jwtProperties.refreshTokenExpiresTime())
                .build();
        tokenRepository.save(token);
        return token;
    }

    public Boolean validateRefreshToken(Token.Key key, String refreshToken) {
        return tokenRepository.findById(key)
                .map(token -> token.getRefreshToken().equals(refreshToken))
                .orElse(Boolean.FALSE);
    }


    private String buildRefreshToken(String id, String subject, Instant now) {
        return buildToken(id, subject, now,
                now.plus(jwtProperties.getRefreshTokenExpiresTime(), jwtProperties.getRefreshTokenExpiresUnit()),
                claim -> claim.putAll(Collections.emptyMap())
        );
    }

    private String buildAccessToken(String id, String subject, Instant now, Map<String, Object> info) {
        return buildToken(id, subject, now,
                now.plus(jwtProperties.getAccessTokenExpiresTime(), jwtProperties.getAccessTokenExpiresUnit()),
                claim -> claim.putAll(info)
        );
    }

    private String buildToken(String id, String subject, Instant now, Instant expires, Consumer<Map<String, Object>> claimsConsumer) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(expires)
                .notBefore(now)
                .id(id)
                .claims(claimsConsumer)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}
