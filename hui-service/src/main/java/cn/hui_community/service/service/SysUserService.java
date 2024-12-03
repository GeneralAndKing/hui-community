package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.request.AddSysUserRequest;
import cn.hui_community.service.model.dto.request.UpdateRolesRequest;
import cn.hui_community.service.model.dto.request.UpdateSysUserRequest;
import cn.hui_community.service.model.dto.response.SysUserPageResponse;
import cn.hui_community.service.model.dto.response.SysUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService {

    SysUserResponse addSysUser(AddSysUserRequest request, String communityId);

    Page<SysUserPageResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable);

    SysUserResponse assignRoles(String sysUserId, UpdateRolesRequest request);

    SysUserResponse cancelRoles(String sysUserId, UpdateRolesRequest request);

    void updatePassword(String sysUserId, String newPassword);

    SysUserResponse updateSysUser(String sysUserId, UpdateSysUserRequest request);

    Boolean checkUsername(String username);

    SysUserResponse lock(String sysUserId);

    SysUserResponse unlock(String sysUserId);
}
