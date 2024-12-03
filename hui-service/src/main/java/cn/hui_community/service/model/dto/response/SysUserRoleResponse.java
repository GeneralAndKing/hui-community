package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@SuperBuilder
public class SysUserRoleResponse extends BaseResponse {
    private String communityName;
    private String communityId;
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;
}
