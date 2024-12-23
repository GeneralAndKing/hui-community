package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.request.AddCommunityRequest;
import cn.hui_community.service.model.dto.request.AddSysUserRoleRequest;
import cn.hui_community.service.model.dto.request.UpdateCommunityRequest;
import cn.hui_community.service.model.dto.request.UpdateSysUserUserRoleRequest;
import cn.hui_community.service.model.dto.response.CommunityResponse;
import cn.hui_community.service.model.dto.response.CommunitySysUserResponse;
import cn.hui_community.service.model.dto.response.SysUserRolePageResponse;
import cn.hui_community.service.model.dto.response.SysUserRoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityService {
    CommunityResponse addCommunity(AddCommunityRequest request);

    CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request);

    SysUserRoleResponse addSysUserRole(String communityId, AddSysUserRoleRequest request);

    Page<CommunityResponse> page(String likedCode, String likedName, String areaOrParentAreaId, Pageable pageable);

    Page<SysUserRolePageResponse> sysUserRolePage(String communityId, String likedName, List<String> permissionIds, Pageable pageable);

    List<CommunitySysUserResponse> removeRole(String communityId, String sysUserRoleId);

    SysUserRoleResponse updateSysUserRole(String communityId, String sysUserRoleId, UpdateSysUserUserRoleRequest request);
}
