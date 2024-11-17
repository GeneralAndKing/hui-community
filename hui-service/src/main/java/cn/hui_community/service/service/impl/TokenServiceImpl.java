package cn.hui_community.service.service.impl;

import cn.hui_community.service.configuration.JwtConfiguration;
import cn.hui_community.service.model.Token;
import cn.hui_community.service.enums.SubjectEnum;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.repository.TokenRepository;
import cn.hui_community.service.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final SysUserRepository sysUserRepository;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final JwtConfiguration.Properties jwtProperties;
    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Token buildTokenFromSysUser(SysUser user) {
        Instant now = Instant.now();
        Token token = generateNewUserToken(user, now);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public Boolean validateRefreshToken(String id, String refreshToken) {
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            return token.getRefreshToken().equals(refreshToken);
        }
        return Boolean.FALSE;
    }

    private Token generateNewUserToken(SysUser user, Instant now) {
        return new Token()
                .setAccessToken(buildAccessToken(user, now))
                .setRefreshToken(buildRefreshToken(user, now))
                .setId(user.getId())
                .setSubject(SubjectEnum.SYS.getValue())
                .setUsername(user.getUsername());
    }

    private String buildRefreshToken(SysUser user, Instant now) {
        return buildToken(user, now,
                now.plus(jwtProperties.getRefreshTokenExpiresTime(), jwtProperties.getRefreshTokenExpiresUnit()),
                claim -> claim.putAll(Map.of("id", user.getId(), "subject", SubjectEnum.SYS.getValue()))
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
