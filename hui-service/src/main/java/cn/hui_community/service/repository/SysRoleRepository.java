package cn.hui_community.service.repository;

import cn.hui_community.service.model.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends JpaRepository<SysUserRole, String> {
}
