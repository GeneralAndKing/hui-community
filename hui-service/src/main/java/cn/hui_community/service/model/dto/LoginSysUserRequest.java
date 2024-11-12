package cn.hui_community.service.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginSysUserRequest {
    private String username;

    private String password;
}
