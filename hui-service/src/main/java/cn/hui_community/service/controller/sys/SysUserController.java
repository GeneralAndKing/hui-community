package cn.hui_community.service.controller.sys;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.model.dto.AssignRolesRequest;
import cn.hui_community.service.model.dto.SysUserResponse;
import cn.hui_community.service.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys-user")
@RequiredArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    @GetMapping("/my")
    public SysUserResponse my() {
        return AuthHelper.currentSysUser().toResponse();
    }
    @PostMapping("/{sysUserId}/roles")
    @PreAuthorize("hasAuthority(auth.SUPER_PERMISSION_NAME+'_001') or auth.hasAssignedRoles(request.roleIds)")
    public SysUserResponse assignRoles(AssignRolesRequest request, @PathVariable String sysUserId){
        return sysUserService.assignRoles(sysUserId,request);

    }

}
