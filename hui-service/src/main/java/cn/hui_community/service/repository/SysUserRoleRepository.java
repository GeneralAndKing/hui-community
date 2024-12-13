package cn.hui_community.service.repository;

import cn.hui_community.service.model.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String>, JpaSpecificationExecutor<SysUserRole> {

    Optional<SysUserRole> findByCommunityIdAndName(String communityId, String name);

    Optional<SysUserRole> findByCommunityIdAndId(String communityId, String id);

}
