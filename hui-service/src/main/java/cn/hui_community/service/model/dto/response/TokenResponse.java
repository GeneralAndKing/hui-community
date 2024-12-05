package cn.hui_community.service.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {


    private String id;

    private String subject;

    private String username;

    private String accessToken;


    private String refreshToken;
}
