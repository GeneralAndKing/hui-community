package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.AuthHelper;
import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.Community;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.dto.request.AddSysUserRequest;
import cn.hui_community.service.model.dto.request.UpdateRolesRequest;
import cn.hui_community.service.model.dto.request.UpdateSysUserRequest;
import cn.hui_community.service.model.dto.response.CommunitySysUserResponse;
import cn.hui_community.service.model.dto.response.SysUserResponse;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.repository.SysUserRoleRepository;
import cn.hui_community.service.service.SysUserService;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .roles(Collections.singleton(AuthHelper.visitorSysUserRole(communityId)))
                .displayName(request.getDisplayName()).build()
        ).toResponse();
    }

    @Override
    public Page<CommunitySysUserResponse> page(String communityId, String likedUsername, String likedDisplayName, Pageable pageable) {
        Page<SysUser> sysUserPage = sysUserRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(communityId)) {
                predicates.add(criteriaBuilder.equal(root.join("roles", JoinType.INNER).get("communityId"), communityId));
            }

            if (StringUtils.isNotBlank(likedUsername)) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + likedUsername + "%"));
            }

            if (StringUtils.isNotBlank(likedDisplayName)) {
                predicates.add(criteriaBuilder.like(root.get("displayName"), "%" + likedDisplayName + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(sysUserPage, (sysUser -> sysUser.toCommunityResponse(communityId)));
    }

    @Override
    public SysUserResponse assignRoles(String sysUserId, UpdateRolesRequest request) {
        SysUser sysUser = sysUserRepository.findById(sysUserId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sysUser", sysUserId));
        List<SysUserRole> roles = sysUserRoleRepository.findAllById(request.getRoleIds());
        sysUser.setRoles(new HashSet<>(roles));
        return sysUserRepository.save(sysUser).toResponse();
    }

    @Override
    public SysUserResponse cancelRoles(String sysUserId, UpdateRolesRequest request) {
        SysUser sysUser = sysUserRepository.findById(sysUserId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sysUser", sysUserId));
        List<SysUserRole> roles = sysUserRoleRepository.findAllById(request.getRoleIds());
        roles.forEach(sysUser.getRoles()::remove);
        return sysUserRepository.save(sysUser).toResponse();
    }

    @Override
    public void updatePassword(String sysUserId, String newPassword) {
        SysUser sysUser = sysUserRepository.findById(sysUserId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sysUser", sysUserId));
        sysUser.setPassword(passwordEncoder.encode(newPassword));
        sysUserRepository.save(sysUser);
    }

    @Override
    public SysUserResponse updateSysUser(String sysUserId, UpdateSysUserRequest request) {
        SysUser sysUser = sysUserRepository.findById(sysUserId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sysUser", sysUserId));
        if ((!sysUser.getUsername().equals(request.getUsername())) && checkUsername(request.getUsername())) {
            throw ResponseStatusExceptionHelper.badRequest("username %s already exists", request.getUsername());
        }
        sysUser.setPhone(request.getPhone())
                .setDisplayName(request.getDisplayName())
                .setUsername(request.getUsername())
                .setEmail(request.getEmail());
        return sysUserRepository.save(sysUser).toResponse();
    }

    public Boolean checkUsername(String username) {
        return sysUserRepository.existsByUsername(username);
    }

    @Override
    public SysUserResponse lock(String sysUserId) {
        SysUser sysUser = sysUserRepository.findById(sysUserId).
                orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sys user", sysUserId));
        sysUser.setLockedTime(Instant.now());
        return sysUserRepository.save(sysUser).toResponse();
    }

    @Override
    public SysUserResponse unlock(String sysUserId) {
        SysUser sysUser = sysUserRepository.findById(sysUserId).
                orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("can't found %s sys user", sysUserId));
        sysUser.setLockedTime(null);
        return sysUserRepository.save(sysUser).toResponse();
    }
}
