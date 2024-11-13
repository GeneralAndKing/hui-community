package cn.hui_community.service.repository;

import cn.hui_community.service.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, String> {
    Optional<SysPermission> findByName(String name);
}
