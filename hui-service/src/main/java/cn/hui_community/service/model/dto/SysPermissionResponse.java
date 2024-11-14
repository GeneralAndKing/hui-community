package cn.hui_community.service.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SysPermissionResponse extends BaseResponse {

    private String name;

    private String description;
}
