package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, String> {
}
