package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopTypeRepository extends JpaRepository<ShopType, String> {
}
