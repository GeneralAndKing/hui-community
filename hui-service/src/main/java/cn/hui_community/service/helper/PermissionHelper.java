package cn.hui_community.service.helper;

import cn.hui_community.service.model.SysPermission;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.SysPermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class PermissionHelper {

    private final SysPermissionRepository sysPermissionRepository;
    private final CommunityRepository communityRepository;
    private static final List<Pair<String, String>> assignedSysPermissions = List.of(
            Pair.of("VISIT", "The visit permission is the initial permission for each community and is used to bind the community to which the user belongs"),
            Pair.of("ADMIN", "The Admin permission can manage their associated communities")

    );
    private static final List<Pair<String, String>> hiddenSysPermissionList = List.of(
            Pair.of("SUPER", "The super permission is used for platform administrator")
    );

    private static final Map<String, SysPermission> sysPermissionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        for (Pair<String, String> sysPermissionTriple : ListUtils.union(assignedSysPermissions, hiddenSysPermissionList)) {
            SysPermission sysPermission = sysPermissionRepository.findByName(sysPermissionTriple.getLeft()).or(() -> Optional.of(
                    sysPermissionRepository.save(SysPermission.builder()
                            .name(sysPermissionTriple.getLeft())
                            .description(sysPermissionTriple.getRight())
                            .build()
                    ))).get();
            sysPermissionMap.put(sysPermission.getId(), sysPermission);
        }

    }

    public static List<SysPermission> currentUserPermissions() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().flatMap(grantedAuthority -> {
                    if (grantedAuthority instanceof SysPermission) {
                        return Stream.of((SysPermission) grantedAuthority);
                    }
                    return Stream.empty();
                }).toList();
    }

    public static Boolean currentUserHasGrantedAuthority(String authority) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }


    public static SysPermission VisitPermission() {
        return sysPermissionMap.get("VISIT");
    }

    public static List<SysPermission> assignedPermissions() {
        return assignedSysPermissions.stream().map(stringStringPair -> sysPermissionMap.get(stringStringPair.getLeft()))
                .toList();
    }

}
