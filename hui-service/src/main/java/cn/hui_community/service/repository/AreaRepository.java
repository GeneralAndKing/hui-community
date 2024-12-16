package cn.hui_community.service.repository;

import cn.hui_community.service.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, String> {

    List<Area> findAllByParentId(String parentId);
}
