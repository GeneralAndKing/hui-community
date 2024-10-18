package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopPrimaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPrimaryTypeRepository extends JpaRepository<ShopPrimaryType, String> {
}
