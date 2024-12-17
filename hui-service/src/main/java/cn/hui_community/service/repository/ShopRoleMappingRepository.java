package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRoleMappingRepository extends JpaRepository<ShopRoleMapping, String> {
}
