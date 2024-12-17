package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.request.AddShopCategoryRequest;
import cn.hui_community.service.model.dto.request.UpdateShopCategoryRequest;
import cn.hui_community.service.model.dto.response.ShopCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategoryResponse> queryByParams(String parentId, Boolean all);

    Page<ShopCategoryResponse> pageByParams(String parentId, String likedName, Pageable pageable);

    ShopCategoryResponse add(AddShopCategoryRequest request);

    void remove(String shopCategoryId);

    ShopCategoryResponse update(String shopCategoryId, UpdateShopCategoryRequest request);
}
