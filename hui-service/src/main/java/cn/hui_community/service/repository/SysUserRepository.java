package cn.hui_community.service.repository;

import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {

    Optional<SysUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<SysUser> findAllByRolesContains(SysUserRole role);


}
