package cn.hui_community.service.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class AddSysUserRequest {

    private String displayName;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Instant expiredTime;
}
