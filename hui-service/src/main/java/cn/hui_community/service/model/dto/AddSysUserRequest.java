package cn.hui_community.service.model.dto;

import jakarta.persistence.Column;
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

    private Instant expiredTime;
}
