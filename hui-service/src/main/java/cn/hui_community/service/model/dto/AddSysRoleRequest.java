package cn.hui_community.service.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class AddSysRoleRequest {
    private Set<String> permissionIds;
    private String name;
    private String description;
}
