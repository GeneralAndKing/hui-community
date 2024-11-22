package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.AddSysUserRequest;
import cn.hui_community.service.model.dto.AssignRolesRequest;
import cn.hui_community.service.model.dto.SysUserPageResponse;
import cn.hui_community.service.model.dto.SysUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysUserService {

    SysUserResponse addSysUser(AddSysUserRequest request, String communityId);

    Page<SysUserPageResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable);

    SysUserResponse assignRoles(String sysUserId, AssignRolesRequest request);
}
