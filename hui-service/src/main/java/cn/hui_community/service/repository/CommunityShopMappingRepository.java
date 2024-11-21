package cn.hui_community.service.repository;

import cn.hui_community.service.model.CommunityShopMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityShopMappingRepository extends JpaRepository<CommunityShopMapping, String>, JpaSpecificationExecutor<CommunityShopMapping> {

    Page<CommunityShopMapping> findAllByCommunityId(String communityId, Pageable pageable);

}
