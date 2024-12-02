package cn.hui_community.service.component;

import cn.hui_community.service.configuration.JwtConfiguration;
import cn.hui_community.service.enums.PermissionTypeEnum;
import cn.hui_community.service.model.Token;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TokenFactory {


    private final JwtEncoder jwtEncoder;
    private final JwtConfiguration.Properties jwtProperties;
    private final TokenRepository tokenRepository;

    @Transactional(rollbackFor = Exception.class)
    public Token buildFromSysUser(SysUser user) {
        Instant now = Instant.now();
        Token token = Token.builder()
                .accessToken(buildAccessToken(user.getId(), PermissionTypeEnum.SYS.getValue(), now, user.toTokenInfo()))
                .refreshToken(buildRefreshToken(user.getId(), PermissionTypeEnum.SYS.getValue(), now))
                .id(user.getId())
                .subject(PermissionTypeEnum.SYS.getValue())
                .username(user.getUsername()).build();
        tokenRepository.save(token);
        return token;
    }

    @Transactional(rollbackFor = Exception.class)
    public Token buildFromUser(User user) {
        Instant now = Instant.now();
        Token token = Token.builder()
                .accessToken(buildAccessToken(user.getId(), PermissionTypeEnum.USER.getValue(), now, Map.of()))
                .refreshToken(buildRefreshToken(user.getId(), PermissionTypeEnum.USER.getValue(), now))
                .id(user.getId())
                .subject(PermissionTypeEnum.USER.getValue())
                .username(user.getName()).build();
        tokenRepository.save(token);
        return token;
    }

    public Boolean validateRefreshToken(String id, String refreshToken) {
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            return token.getRefreshToken().equals(refreshToken);
        }
        return Boolean.FALSE;
    }


    private String buildRefreshToken(String id, String subject, Instant now) {
        return buildToken(id, subject, now,
                now.plus(jwtProperties.getRefreshTokenExpiresTime(), jwtProperties.getRefreshTokenExpiresUnit()),
                claim -> claim.putAll(Map.of("id", id, "subject", subject))
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
