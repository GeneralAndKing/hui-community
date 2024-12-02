package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.dto.AddSysUserRequest;
import cn.hui_community.service.model.dto.AssignRolesRequest;
import cn.hui_community.service.model.dto.SysUserPageResponse;
import cn.hui_community.service.model.dto.SysUserResponse;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.repository.SysUserRoleRepository;
import cn.hui_community.service.service.SysUserService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;
    private final SysUserRoleRepository sysUserRoleRepository;
    private final CommunityRepository communityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUserResponse addSysUser(AddSysUserRequest request, String communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s community", communityId));
        if (sysUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw ResponseStatusExceptionHelper.badRequest("username %s already exists", request.getUsername());
        }
        return sysUserRepository.save(SysUser.builder()
                .phone(request.getPhone())
                .expiredTime(request.getExpiredTime())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .roles(Collections.singleton(AuthHelper.visitorSysUserRole(communityId)))
                .displayName(request.getDisplayName()).build()
        ).toResponse();
    }

    @Override
    public Page<SysUserPageResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable) {
        Page<SysUser> sysUserPage = sysUserRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(communityId)) {
                predicates.add(criteriaBuilder.equal(root.get("communityId"), communityId));
            }

            if (StringUtils.isNotBlank(likedUsername)) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + likedUsername + "%"));
            }

            if (StringUtils.isNotBlank(likedDisplayName)) {
                predicates.add(criteriaBuilder.like(root.get("displayName"), "%" + likedDisplayName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(sysUserPage, SysUser::toPageResponse);
    }

    @Override
    public SysUserResponse assignRoles(String sysUserId, AssignRolesRequest request) {
        SysUser sysUser = sysUserRepository.findById(sysUserId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sysUser", sysUserId));
        List<SysUserRole> roles = sysUserRoleRepository.findAllById(request.getRoleIds());
        sysUser.setRoles(new HashSet<>(roles));
        return sysUserRepository.save(sysUser).toResponse();
    }
}
