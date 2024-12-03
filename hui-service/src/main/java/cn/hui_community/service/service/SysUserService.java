package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService {

    SysUserResponse addSysUser(AddSysUserRequest request, String communityId);

    Page<SysUserPageResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable);

    SysUserResponse assignRoles(String sysUserId, RolesRequest request);

    SysUserResponse cancelRoles(String sysUserId, RolesRequest request);

    void updatePassword(String sysUserId, String newPassword);

    SysUserResponse updateSysUser(String sysUserId, UpdateSysUserRequest request);

    Boolean checkUsername(String username);

    SysUserResponse lock(String sysUserId);

    SysUserResponse unlock(String sysUserId);
}
