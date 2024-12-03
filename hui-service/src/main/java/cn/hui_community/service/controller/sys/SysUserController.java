package cn.hui_community.service.controller.sys;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.model.dto.request.UpdateRolesRequest;
import cn.hui_community.service.model.dto.response.SysUserResponse;
import cn.hui_community.service.model.dto.request.UpdateSysUserRequest;
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


    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestParam String username) {
        return sysUserService.checkUsername(username);
    }

    @PutMapping("/{sysUserId}/password")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.currentSysUser().id==#sysUserId")
    public void updatePassword(@PathVariable String sysUserId, String newPassword) {
        sysUserService.updatePassword(sysUserId, newPassword);
    }


    @PutMapping("/{sysUserId}")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.currentSysUser().id==#sysUserId")
    public SysUserResponse updateSysUser(@PathVariable String sysUserId, @RequestBody UpdateSysUserRequest request) {
        return sysUserService.updateSysUser(sysUserId, request);

    }

    @PostMapping("/{sysUserId}/roles")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.hasAssignedRolesAuthority(request.roleIds)")
    public SysUserResponse assignRoles(@PathVariable String sysUserId, @RequestBody UpdateRolesRequest request) {
        return sysUserService.assignRoles(sysUserId, request);
    }

    @DeleteMapping("/{sysUserId}/roles")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.hasAssignedRolesAuthority(request.roleIds)")
    public SysUserResponse cancelRoles(@PathVariable String sysUserId, @RequestBody UpdateRolesRequest request) {
        return sysUserService.cancelRoles(sysUserId, request);
    }

    @PostMapping("/{sysUserId}/lock")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public SysUserResponse lock(@PathVariable String sysUserId) {
        return sysUserService.lock(sysUserId);
    }
    @DeleteMapping("/{sysUserId}/lock")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public SysUserResponse unlock(@PathVariable String sysUserId) {
        return sysUserService.unlock(sysUserId);
    }

}
