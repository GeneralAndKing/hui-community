package cn.hui_community.service.helper;

import cn.hui_community.service.model.SysPermission;
import cn.hui_community.service.repository.SysPermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class SysPermissionHelper {

    private final SysPermissionRepository sysPermissionRepository;
    private static final List<Pair<String, String>> initSysPermissionList = List.of(
            Pair.of("VISIT", "The visit permission is the initial permission for each community and is used to bind the community to which the user belongs"),
            Pair.of("SUPER", "The super permission is used for platform administrator")


    );

    private static final Map<String, SysPermission> sysPermissionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        for (Pair<String, String> sysPermissionTriple : initSysPermissionList) {
            SysPermission sysPermission = sysPermissionRepository.findByName(sysPermissionTriple.getLeft()).or(() -> Optional.of(
                    sysPermissionRepository.save(SysPermission.builder()
                            .name(sysPermissionTriple.getLeft())
                            .description(sysPermissionTriple.getRight())
                            .build()
                    ))).get();
            sysPermissionMap.put(sysPermission.getId(), sysPermission);
        }

    }

    public static SysPermission VISIT() {
        return sysPermissionMap.get("VISIT");
    }

}
