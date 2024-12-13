package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.request.AddCommunityRequest;
import cn.hui_community.service.model.dto.request.AddSysRoleRequest;
import cn.hui_community.service.model.dto.request.AddSysUserRequest;
import cn.hui_community.service.model.dto.request.UpdateCommunityRequest;
import cn.hui_community.service.model.dto.response.*;
import cn.hui_community.service.service.CommunityService;
import cn.hui_community.service.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/page")
//    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public Page<CommunityResponse> communityPage(
            @RequestParam(required = false) String areaOrParentAreaId,
            @RequestParam(required = false) String likedName,
            @RequestParam(required = false) String likedCode, Pageable pageable) {
        return communityService.page(likedCode, likedName, areaOrParentAreaId, pageable);
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
    public Page<CommunitySysUserResponse> sysUserPage(@PathVariable String communityId,
                                                      @RequestParam(required = false) String likedUsername,
                                                      @RequestParam(required = false) String likedDisplayName, Pageable pageable) {
        return sysUserService.page(communityId, likedUsername, likedDisplayName, pageable);
    }

    @PostMapping("/{communityId}/role")
    @PreAuthorize("hasAuthority(@auth.ADMIN_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public SysUserRoleResponse addNewSysUserRole(@PathVariable String communityId, AddSysRoleRequest request) {
        return communityService.addSysUserRole(communityId, request);
    }

    @GetMapping("/{communityId}/role/page")
    @PreAuthorize("hasAuthority(@auth.ADMIN_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public Page<SysUserRolePageResponse> remove(@PathVariable String communityId,

                                                         @RequestParam(required = false) List<String> permissionIds,
                                                         @RequestParam(required = false) String likedName,Pageable pageable
                                                         ) {
        return communityService.sysUserRolePage(communityId, likedName,permissionIds,pageable);
    }

    @DeleteMapping("/{communityId}/role/{sysUserRoleId}")
    @PreAuthorize("hasAuthority(@auth.ADMIN_AUTHORITY_PREFIX+#communityId) or hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public List<CommunitySysUserResponse> removeRole(@PathVariable String communityId,
                                                     @PathVariable String sysUserRoleId) {
        return communityService.removeRole(communityId, sysUserRoleId);
    }
}
