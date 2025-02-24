package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.request.UpdateShowRoleRequest;
import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.model.dto.response.ShopRoleMappingResponse;
import cn.hui_community.service.model.dto.response.ShopSysShowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService {
    Page<ShopSysShowResponse> pageByParams(String communityId, List<String> categoryIds, String likedName, Pageable pageable);

    ShopDetailResponse detail(String shopId);


    ShopRoleMappingResponse updateRole(String shopId, UpdateShowRoleRequest request);
}
