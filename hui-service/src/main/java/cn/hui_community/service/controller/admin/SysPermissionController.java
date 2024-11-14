package cn.hui_community.service.controller.admin;

import cn.hui_community.service.helper.PermissionHelper;
import cn.hui_community.service.model.SysPermission;
import cn.hui_community.service.model.dto.SysPermissionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sys-permission")
public class SysPermissionController {

    @GetMapping("")
    @PreAuthorize("hasAuthority('SUPER') or hasAuthority('ADMIN')")
    public List<SysPermissionResponse> all() {
        return PermissionHelper.assignedPermissions()
                .stream()
                .map(SysPermission::toResponse).toList();
    }
}
