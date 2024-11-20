package cn.hui_community.service.helper;

import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.PermissionRepository;
import cn.hui_community.service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;


@Component
public class AuthHelper {

    private static PermissionRepository permissionRepository;
    private static CommunityRepository communityRepository;
    private static UserRepository userRepository;

    private AuthHelper(PermissionRepository permissionRepository, CommunityRepository communityRepository, UserRepository userRepository) {
        AuthHelper.communityRepository = communityRepository;
        AuthHelper.permissionRepository = permissionRepository;
        AuthHelper.userRepository = userRepository;

    }

    private static final List<Triple<String, String, String>> assignedSysPermissions = List.of(
            Triple.of("VISIT", "SYS", "The visit permission is the initial permission for each community and is used to bind the community to which the user belongs"),
            Triple.of("ADMIN", "SYS", "The Admin permission can manage their associated communities")

    );
    private static final List<Triple<String, String, String>> hiddenSysPermissionList = List.of(
            Triple.of("SUPER", "SYS", "The super permission is used for platform administrator")
    );

    private static final Map<String, Permission> sysPermissionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        for (Triple<String, String, String> permissionTriple : ListUtils.union(assignedSysPermissions, hiddenSysPermissionList)) {
            Permission permission = permissionRepository.findByName(permissionTriple.getLeft()).or(() -> Optional.of(
                    permissionRepository.save(Permission.builder()
                            .name(permissionTriple.getLeft())
                            .type(permissionTriple.getMiddle())
                            .description(permissionTriple.getRight())
                            .build()
                    ))).get();
            sysPermissionMap.put(permission.getId(), permission);
        }

    }

    public static List<Permission> currentUserPermissions() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().flatMap(grantedAuthority -> {
                    if (grantedAuthority instanceof Permission) {
                        return Stream.of((Permission) grantedAuthority);
                    }
                    return Stream.empty();
                }).toList();
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return userRepository.findById(jwtAuthenticationToken.getToken().getId())
                    .orElseThrow(ResponseStatusExceptionHelper.unauthorizedSupplier("can't found current user."));
        }
        throw ResponseStatusExceptionHelper.unauthorized("can't found current user.");
    }


    public static Permission VisitSysPermission() {
        return sysPermissionMap.get("VISIT");
    }

    public static List<Permission> assignedPermissions() {
        return assignedSysPermissions.stream().map(stringStringPair -> sysPermissionMap.get(stringStringPair.getLeft()))
                .toList();
    }

}
