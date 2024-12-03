package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.response.ShopShowResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    Page<ShopShowResponse> pageByParams(String communityId, String categoryId, Float longitude, Float latitude, Pageable pageable);

}
