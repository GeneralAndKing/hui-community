package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.request.AddCommunityRequest;
import cn.hui_community.service.model.dto.request.AddSysRoleRequest;
import cn.hui_community.service.model.dto.request.UpdateCommunityRequest;
import cn.hui_community.service.model.dto.response.CommunityResponse;
import cn.hui_community.service.model.dto.response.SysUserRoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityService {
    CommunityResponse addCommunity(AddCommunityRequest request);

    CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request);

    SysUserRoleResponse addSysRole(String communityId, AddSysRoleRequest request);

    Page<CommunityResponse> page(String likedCode, String likedName, String areaOrParentAreaId, Pageable pageable);
}
