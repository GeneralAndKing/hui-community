package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.*;

public interface CommunityService {
    CommunityResponse addCommunity(AddCommunityRequest request);

    CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request);

    SysUserRoleResponse addSysRole(String communityId, AddSysRoleRequest request);
}
