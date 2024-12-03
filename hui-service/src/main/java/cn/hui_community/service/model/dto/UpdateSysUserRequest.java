package cn.hui_community.service.model.dto;

import lombok.Data;

@Data
public class UpdateSysUserRequest {
    private String displayName;

    private String username;

    private String phone;

    private String email;

}
