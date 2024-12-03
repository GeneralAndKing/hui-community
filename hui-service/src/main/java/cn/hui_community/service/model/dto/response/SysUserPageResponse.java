package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Set;

@SuperBuilder
@Getter
public class SysUserPageResponse extends BaseResponse {
    private String displayName;

    private String username;

    private String phone;

    private String email;

    private Instant lockedTime;
    private Instant expiredTime;

    private Set<String> roles;
}
