package cn.hui_community.service.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SysUserResponse extends BaseResponse {

    private String displayName;

    private String username;

    private String password;

    private String phone;

    private Boolean locked;
}
