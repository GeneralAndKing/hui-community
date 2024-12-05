package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.TokenResponse;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@RedisHash(value = "token")
@Builder
public class Token {

    @Builder
    @AllArgsConstructor
    @Data
    @EqualsAndHashCode
    @Embeddable
    @NoArgsConstructor
    public static class Key implements Serializable {
        private String id;
        private String subject;

        @Override
        public String toString() {
            return id + ":" + subject;
        }
    }

    @Id
    @EmbeddedId
    private Key key;

    private String username;
    private String accessToken;
    private String refreshToken;

    @TimeToLive
    private Long timeToLive;

    public TokenResponse toResponse() {
        return TokenResponse.builder()
                .id(getKey().getId())
                .subject(getKey().getSubject())
                .username(getUsername())
                .accessToken(getAccessToken())
                .refreshToken(getRefreshToken())
                .build();
    }

}