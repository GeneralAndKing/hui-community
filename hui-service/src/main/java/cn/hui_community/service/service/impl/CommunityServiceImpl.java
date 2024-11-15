package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.helper.PermissionHelper;
import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.dto.AddCommunityRequest;
import cn.hui_community.service.model.dto.CommunityResponse;
import cn.hui_community.service.model.dto.UpdateCommunityRequest;
import cn.hui_community.service.repository.AreaRepository;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.SysRoleRepository;
import cn.hui_community.service.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final AreaRepository areaRepository;
    private final SysRoleRepository sysRoleRepository;

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
        sysRoleRepository.save(
                SysUserRole.builder()
                        .community(community)
                        .permissions(Collections.singleton(PermissionHelper.VisitSysPermission()))
                        .name("VISITOR")
                        .build()
        );
        return community.toResponse();

    }

    @Override
    public CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("community does not exist", communityId));
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
}
