package cn.hui_community.service.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class AddSysUserRoleRequest {
    @NotEmpty
    private Set<String> permissionIds;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
