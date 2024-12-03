package cn.hui_community.service.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Set;

@Getter
@SuperBuilder
public class SysUserResponse extends BaseResponse {

    private String displayName;

    private String username;

    private String phone;

    private String email;

    private Instant lockedTime;

    private Set<SysUserRoleResponse> roles;
}
