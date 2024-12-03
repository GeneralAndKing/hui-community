package cn.hui_community.service.controller.sys;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.dto.response.PermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permission")
public class PermissionController {

    @GetMapping("")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.hasAuthorityPrefix(@auth.ADMIN_AUTHORITY_PREFIX)")
    public List<PermissionResponse> all() {
        return AuthHelper.assignedSysPermissions()
                .stream()
                .map(Permission::toResponse).toList();
    }
}
