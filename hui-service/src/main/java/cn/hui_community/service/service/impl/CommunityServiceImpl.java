package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.dto.*;
import cn.hui_community.service.repository.AreaRepository;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.PermissionRepository;
import cn.hui_community.service.repository.SysUserRoleRepository;
import cn.hui_community.service.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final AreaRepository areaRepository;
    private final SysUserRoleRepository sysUserRoleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommunityResponse addCommunity(AddCommunityRequest request) {
        if (!communityRepository.existsByCode(request.getCode())) {
            throw ResponseStatusExceptionHelper.badRequest("%s code exists", request.getCode());
        }
        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("%s area does not exist", request.getAreaId()));
        Community community = communityRepository.save(
                Community.builder()
                        .code(request.getCode())
                        .address(request.getAddress())
                        .area(area)
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .build()
        );
        sysUserRoleRepository.save(
                SysUserRole.builder()
                        .community(community)
                        .permissions(Collections.singleton(AuthHelper.visitSysPermission()))
                        .name(AuthHelper.VISITOR_ROLE_NAME)
                        .build()
        );
        return community.toResponse();

    }

    @Override
    public CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("community %s does not exist", communityId));
        if (!communityRepository.existsByCode(request.getCode())) {
            throw ResponseStatusExceptionHelper.badRequest("%s code exists", request.getCode());
        }
        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("%s area does not exist", request.getAreaId()));

        community
                .setCode(request.getCode())
                .setAddress(request.getAddress())
                .setArea(area)
                .setLatitude(request.getLatitude())
                .setLongitude(request.getLongitude());
        return communityRepository.save(community).toResponse();

    }

    @Override
    public SysUserRoleResponse addSysRole(String communityId, AddSysRoleRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("community %s does not exist", communityId));
        if (sysUserRoleRepository.findByCommunityIdAndName(communityId, request.getName()).isPresent()) {
            throw ResponseStatusExceptionHelper.badRequest("%s role already exists", request.getName());
        }
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());
        if (CollectionUtils.containsAny(permissions, AuthHelper.superSysPermission())) {
            throw ResponseStatusExceptionHelper.badRequest(" %s permission can't assign", AuthHelper.superSysPermission().getName());
        }
        if (permissions.size() != request.getPermissionIds().size()) {
            throw ResponseStatusExceptionHelper.badRequest("%s permissions don't match", request.getPermissionIds().size());
        }
        return sysUserRoleRepository.save(SysUserRole.builder()
                .communityId(communityId)
                .permissions(new HashSet<>(permissions))
                .build()).toResponse();
    }
}
