package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.*;
import cn.hui_community.service.service.CommunityService;
import cn.hui_community.service.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;
    private final SysUserService sysUserService;

    @PostMapping("")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public CommunityResponse addCommunity(@RequestBody AddCommunityRequest request) {
        return communityService.addCommunity(request);
    }


    @PutMapping("/{communityId}")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public CommunityResponse updateCommunityById(@PathVariable String communityId, @RequestBody UpdateCommunityRequest request) {
        return communityService.updateCommunityById(communityId, request);
    }

    @PostMapping("/{communityId}/sys-user")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public SysUserResponse addSysUser(@PathVariable String communityId, @RequestBody AddSysUserRequest request) {
        return sysUserService.addSysUser(request, communityId);
    }

    @GetMapping("/{communityId}/sys-user/page")
    @PreAuthorize("hasAuthority(@auth.ADMIN_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public Page<SysUserPageResponse> sysUserPage(@PathVariable String communityId,
                                                 @RequestParam(required = false) String likedUsername,
                                                 @RequestParam(required = false) String likedDisplayName,
                                                 @RequestParam Pageable pageable) {
        return sysUserService.page(communityId, likedUsername, likedDisplayName, pageable);
    }

    @PostMapping("/{communityId}/role")
    @PreAuthorize("hasAuthority(@auth.ADMIN_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public SysUserRoleResponse addNewSysUserRole(@PathVariable String communityId, AddSysRoleRequest request) {
        return communityService.addSysRole(communityId, request);
    }
}
