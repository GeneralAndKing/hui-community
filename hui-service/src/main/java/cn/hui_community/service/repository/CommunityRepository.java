package cn.hui_community.service.repository;

import cn.hui_community.service.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, String> {
    Optional<Community> findByCode(String code);

    Boolean existsByCode(String code);
}
