package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.AddSysUserRequest;
import cn.hui_community.service.model.dto.RolesRequest;
import cn.hui_community.service.model.dto.SysUserPageResponse;
import cn.hui_community.service.model.dto.SysUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService {

    SysUserResponse addSysUser(AddSysUserRequest request, String communityId);

    Page<SysUserPageResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable);

    SysUserResponse assignRoles(String sysUserId, RolesRequest request);

    SysUserResponse cancelRoles(String sysUserId, RolesRequest request);
}
