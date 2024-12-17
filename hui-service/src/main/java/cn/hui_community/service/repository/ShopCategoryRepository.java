package cn.hui_community.service.repository;

import cn.hui_community.service.model.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShopCategoryRepository extends JpaRepository<ShopCategory, String>, JpaSpecificationExecutor<ShopCategory> {
    List<ShopCategory> findAllByParentId(String parentId);
}
