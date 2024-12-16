package cn.hui_community.service.helper;

import cn.hui_community.service.enums.PermissionTypeEnum;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.SysUserRole;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;


@Component(value = "auth")
public class AuthHelper {
    private static SysUserRepository sysUserRepository;
    private static SysUserRoleRepository sysUserRoleRepository;
    private static PermissionRepository permissionRepository;
    private static CommunityRepository communityRepository;
    private static UserRepository userRepository;

    public static final String VISITOR_ROLE_NAME = "VISITOR";
    public static final String VISIT_PERMISSION_NAME = "VISIT";
    public static final String VISIT_AUTHORITY_PREFIX = PermissionTypeEnum.SYS.getValue() + "_" + VISIT_PERMISSION_NAME + "_";

    public static final String ADMIN_PERMISSION_NAME = "ADMIN";
    public static final String ADMIN_AUTHORITY_PREFIX = PermissionTypeEnum.SYS.getValue() + "_" + ADMIN_PERMISSION_NAME + "_";

    public static final String SUPER_PERMISSION_NAME = "SUPER_ADMIN";
    public static final String SUPER_AUTHORITY_PREFIX = PermissionTypeEnum.SYS.getValue() + "_" + SUPER_PERMISSION_NAME + "_";


    /**
     * A list of Triple objects representing the assigned system permissions.
     * Each Triple contains the permission name, permission type, and a description of the permission.
     *
     * @see Triple
     * @see PermissionTypeEnum
     */
    private static final List<Triple<String, String, String>> assignedSysPermissions = List.of(
            Triple.of(VISIT_PERMISSION_NAME, PermissionTypeEnum.SYS.getValue(), "The visit permission is the initial permission for each community and is used to bind the community to which the user belongs"),
            Triple.of(ADMIN_PERMISSION_NAME, PermissionTypeEnum.SYS.getValue(), "The Admin permission can manage their associated communities")
    );
    
    /**
     * A list of Triple objects representing hidden system permissions.
     * Each Triple contains the permission name, permission type, and a description of the permission.
     *
     * @see Triple
     * @see PermissionTypeEnum
     */
    private static final List<Triple<String, String, String>> hiddenSysPermissionList = List.of(
            Triple.of(SUPER_PERMISSION_NAME, PermissionTypeEnum.SYS.getValue(), "The super permission is used for platform administrator")
    );

    private static final Map<String, Permission> sysPermissionMap = new HashMap<>();

    private AuthHelper(PermissionRepository permissionRepository,
                       CommunityRepository communityRepository,
                       UserRepository userRepository,
                       SysUserRepository sysUserRepository,
                       SysUserRoleRepository sysUserRoleRepository) {
        AuthHelper.communityRepository = communityRepository;
        AuthHelper.permissionRepository = permissionRepository;
        AuthHelper.userRepository = userRepository;
        AuthHelper.sysUserRepository = sysUserRepository;
        AuthHelper.sysUserRoleRepository = sysUserRoleRepository;

        for (Triple<String, String, String> permissionTriple : ListUtils.union(assignedSysPermissions, hiddenSysPermissionList)) {
            Permission permission = permissionRepository.findByName(permissionTriple.getLeft()).or(() -> Optional.of(
                    permissionRepository.save(Permission.builder()
                            .name(permissionTriple.getLeft())
                            .type(permissionTriple.getMiddle())
                            .description(permissionTriple.getRight())
                            .build()
                    ))).get();
            sysPermissionMap.put(permissionTriple.getLeft(), permission);
        }
    }

    
    public static User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return userRepository.findById(jwtAuthenticationToken.getToken().getId())
                    .orElseThrow(ResponseStatusExceptionHelper.unauthorizedSupplier("can't found current user."));
        }
        throw ResponseStatusExceptionHelper.unauthorized("can't found current user.");
    }

    public static SysUser currentSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return sysUserRepository.findById(jwtAuthenticationToken.getToken().getId())
                    .orElseThrow(ResponseStatusExceptionHelper.unauthorizedSupplier("can't found current sys user."));
        }
        throw ResponseStatusExceptionHelper.unauthorized("can't found current sys user.");
    }


    public static Permission visitSysPermission() {
        return sysPermissionMap.get(VISIT_PERMISSION_NAME);
    }

    public static Permission superSysPermission() {
        return sysPermissionMap.get(SUPER_PERMISSION_NAME);
    }


    public static SysUserRole visitorSysUserRole(String communityId) {
        return sysUserRoleRepository.findByCommunityIdAndName(communityId, VISITOR_ROLE_NAME).get();
    }


    public static List<Permission> assignedSysPermissions() {
        return assignedSysPermissions.stream().map(stringStringPair -> sysPermissionMap.get(stringStringPair.getLeft()))
                .toList();
    }


    /**
     * This method checks if the current SysUser has the authority to assign the given roles.
     * It verifies if the current SysUser has the ADMIN_PERMISSION_NAME for each community associated with the roles.
     *
     * @param roleIds A set of role IDs to be checked for authority.
     * @return A boolean value indicating whether the current SysUser has the authority to assign all the given roles.
     * Returns true if the current SysUser has the ADMIN_PERMISSION_NAME for each community associated with the roles,
     * and false otherwise.
     */
    public boolean hasAssignedRolesAuthority(Set<String> roleIds) {
        List<String> adminCommunityIds = currentSysUser().getRoles()
                .stream()
                .filter(role -> role.getPermissions().stream()
                        .anyMatch(permission -> permission.getType().equals(PermissionTypeEnum.SYS.getValue()) && permission.getName().equals(ADMIN_PERMISSION_NAME)))
                .map(SysUserRole::getCommunityId).toList();
        List<SysUserRole> roles = sysUserRoleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            return false;
        }
        return CollectionUtils.containsAll(adminCommunityIds, roleIds);
    }

    /**
     * This method checks if the current user has a specific authority prefix.
     * It iterates through the granted authorities of the current user and checks if any authority starts with the given permission prefix.
     *
     * @param permissionPrefix The prefix to be checked for authority.
     * @return A boolean value indicating whether the current user has the authority with the given prefix.
     * Returns true if the current user has an authority that starts with the given prefix, and false otherwise.
     */
    public boolean hasAuthorityPrefix(String permissionPrefix) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().startsWith(permissionPrefix)) {
                return true;
            }
        }
        return false;
    }


}
