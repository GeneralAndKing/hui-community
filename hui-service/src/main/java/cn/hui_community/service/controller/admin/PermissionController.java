package cn.hui_community.service.controller.admin;

import cn.hui_community.service.helper.PermissionHelper;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.dto.PermissionResponse;
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
    @PreAuthorize("hasAuthority('SUPER') or hasAuthority('ADMIN')")
    public List<PermissionResponse> all() {
        return PermissionHelper.assignedPermissions()
                .stream()
                .map(Permission::toResponse).toList();
    }
}
