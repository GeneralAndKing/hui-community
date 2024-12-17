package cn.hui_community.service.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.dto.request.AddCommunityRequest;
import cn.hui_community.service.model.dto.request.AddSysRoleRequest;
import cn.hui_community.service.model.dto.request.UpdateCommunityRequest;
import cn.hui_community.service.model.dto.response.CommunityResponse;
import cn.hui_community.service.model.dto.response.CommunitySysUserResponse;
import cn.hui_community.service.model.dto.response.SysUserRolePageResponse;
import cn.hui_community.service.model.dto.response.SysUserRoleResponse;
import cn.hui_community.service.repository.AreaRepository;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.PermissionRepository;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.repository.SysUserRoleRepository;
import cn.hui_community.service.service.CommunityService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final AreaRepository areaRepository;
    private final SysUserRoleRepository sysUserRoleRepository;
    private final PermissionRepository permissionRepository;
    private final SysUserRepository sysUserRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommunityResponse addCommunity(AddCommunityRequest request) {
        if (!communityRepository.existsByCode(request.getCode())) {
            throw ResponseStatusExceptionHelper.badRequest("%s code exists", request.getCode());
        }
        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("%s area does not exist",
                        request.getAreaId()));
        Community community = communityRepository.save(
                Community.builder()
                        .code(request.getCode())
                        .address(request.getAddress())
                        .area(area)
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .build());
        sysUserRoleRepository.save(
                SysUserRole.builder()
                        .community(community)
                        .permissions(Collections.singleton(AuthHelper.visitSysPermission()))
                        .name(AuthHelper.VISITOR_ROLE_NAME)
                        .description(AuthHelper.VISITOR_ROLE_NAME)
                        .build());
        return community.toResponse();

    }

    @Override
    public CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(
                        ResponseStatusExceptionHelper.badRequestSupplier("community %s does not exist", communityId));
        if (!communityRepository.existsByCode(request.getCode())) {
            throw ResponseStatusExceptionHelper.badRequest("%s code exists", request.getCode());
        }
        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("%s area does not exist",
                        request.getAreaId()));

        community
                .setCode(request.getCode())
                .setAddress(request.getAddress())
                .setArea(area)
                .setLatitude(request.getLatitude())
                .setLongitude(request.getLongitude());
        return communityRepository.save(community).toResponse();

    }

    @Override
    public SysUserRoleResponse addSysUserRole(String communityId, AddSysRoleRequest request) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(
                        ResponseStatusExceptionHelper.badRequestSupplier("community %s does not exist", communityId));
        if (sysUserRoleRepository.findByCommunityIdAndName(communityId, request.getName()).isPresent()) {
            throw ResponseStatusExceptionHelper.badRequest("%s role already exists", request.getName());
        }
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());
        if (CollectionUtils.containsAny(permissions, AuthHelper.superSysPermission())) {
            throw ResponseStatusExceptionHelper.badRequest(" %s permission can't assign",
                    AuthHelper.superSysPermission().getName());
        }
        if (permissions.size() != request.getPermissionIds().size()) {
            throw ResponseStatusExceptionHelper.badRequest("%s permissions don't match",
                    request.getPermissionIds().size());
        }
        return sysUserRoleRepository.save(SysUserRole.builder()
                .communityId(communityId)
                .permissions(new HashSet<>(permissions))
                .build()).toResponse();
    }

    @Override
    public Page<CommunityResponse> page(String likedCode, String likedName, String areaOrParentAreaId,
            Pageable pageable) {
        ArrayList<String> areaIds = new ArrayList<>();
        if (StringUtils.isNotBlank(areaOrParentAreaId)) {
            Area area = areaRepository.findById(areaOrParentAreaId)
                    .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("area %s does not exist",
                            areaOrParentAreaId));
            if (area.getLevel() < 2) {
                throw ResponseStatusExceptionHelper.badRequest("The area is too large, level is %s", area.getLevel());
            }
            List<Area> processAreas = Collections.singletonList(area);

            areaIds.add(area.getId());
            while (true) {
                ArrayList<Area> tmpAreas = new ArrayList<>();
                for (Area processArea : processAreas) {
                    tmpAreas.addAll(processArea.getChildren());
                }
                if (tmpAreas.isEmpty()) {
                    break;
                }
                areaIds.addAll(tmpAreas.stream().map(Area::getId).toList());
                processAreas = tmpAreas;
            }
        }
        Page<Community> communityPage = communityRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(likedCode)) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + likedCode + "%"));
            }

            if (StringUtils.isNotBlank(likedName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + likedName + "%"));
            }

            if (!areaIds.isEmpty()) {
                predicates.add(root.get("areaId").in(areaIds));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(communityPage, Community::toResponse);
    }

    @Override
    public Page<SysUserRolePageResponse> sysUserRolePage(String communityId, String likedName,
            List<String> permissionIds, Pageable pageable) {
        if (!communityRepository.existsById(communityId)) {
            throw ResponseStatusExceptionHelper.badRequest("community %s does not exist", communityId);
        }
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            List<Permission> permissionList = permissionRepository.findAllById(permissionIds);
            if (!CollectionUtils.isEqualCollection(permissionList.stream().map(Permission::getId).toList(),
                    permissionIds)) {
                throw ResponseStatusExceptionHelper.badRequest("permissionIds don't match");
            }
        }
        Page<SysUserRole> sysUserRolePage = sysUserRoleRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("communityId"), communityId));
            if (CollectionUtils.isNotEmpty(permissionIds)) {
                List<Permission> permissionList = permissionRepository.findAllById(permissionIds);
                if (!CollectionUtils.isEqualCollection(permissionList.stream().map(Permission::getId).toList(),
                        permissionIds)) {
                    throw ResponseStatusExceptionHelper.badRequest("permissionIds don't match");
                }
                predicates.add(root.get("communityId").in(permissionList));
            }
            if (StringUtils.isNotBlank(likedName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + likedName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(sysUserRolePage, SysUserRole::toPageResponse);
    }

    @Override
    public List<CommunitySysUserResponse> removeRole(String communityId, String sysUserRoleId) {
        SysUserRole sysUserRole = sysUserRoleRepository.findByCommunityIdAndId(communityId, sysUserRoleId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("sysUserRole %s does not exist",
                        sysUserRoleId));
        if (sysUserRole.equals(AuthHelper.visitorSysUserRole(communityId))) {
            throw ResponseStatusExceptionHelper.badRequest("Cannot remove visitor role");
        }
        List<SysUser> sysUserList = sysUserRepository.findAllByRolesContains(sysUserRole);
        if (CollectionUtils.isNotEmpty(sysUserList)) {
            sysUserList.forEach(sysUser -> {
                sysUser.getRoles().remove(sysUserRole);
            });
            sysUserList = sysUserRepository.saveAll(sysUserList);
        }
        return sysUserList.stream().map((sysUser) -> sysUser.toCommunityResponse(communityId)).toList();
    }
}
