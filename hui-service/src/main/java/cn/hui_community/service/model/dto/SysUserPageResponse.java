package cn.hui_community.service.model.dto;

import lombok.Builder;
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

    private Instant lockedTime;
    private Instant expiredTime;

    private Set<String> roles;
}
