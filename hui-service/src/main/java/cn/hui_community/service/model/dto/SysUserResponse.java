package cn.hui_community.service.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
public class SysUserResponse extends BaseResponse {

    private String displayName;

    private String username;

    private String password;

    private String phone;

    private Instant lockedTime;
    private Instant expiredTime;
}
