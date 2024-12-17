package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.ShopCategory;
import cn.hui_community.service.model.dto.request.AddShopCategoryRequest;
import cn.hui_community.service.model.dto.request.UpdateShopCategoryRequest;
import cn.hui_community.service.model.dto.response.ShopCategoryResponse;
import cn.hui_community.service.repository.ShopCategoryRepository;
import cn.hui_community.service.service.ShopCategoryService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryRepository shopCategoryRepository;

    @Override
    @Cacheable(value = "shop-category", key = "T(String).valueOf(#all == true ? 'true' : (#parentId ?: 'default'))")
    public List<ShopCategoryResponse> queryByParams(String parentId, Boolean all) {
        if (all != null && all) {
            return shopCategoryRepository.findAll().stream().map(ShopCategory::toResponse).collect(Collectors.toList());
        }
        return shopCategoryRepository.findAllByParentId(parentId).stream().map(ShopCategory::toResponse).collect(Collectors.toList());
    }

    @Override
    public Page<ShopCategoryResponse> pageByParams(String parentId, String likedName, Pageable pageable) {
        Page<ShopCategory> shopCategoryPage = shopCategoryRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(likedName)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + likedName + "%"));
            }
            if (StringUtils.isNotBlank(parentId)) {
                predicates.add(criteriaBuilder.equal(root.get("parentId"), parentId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(shopCategoryPage, ShopCategory::toResponse);
    }

    @Override
    @CacheEvict(value = "shop-category", allEntries = true)
    public ShopCategoryResponse add(AddShopCategoryRequest request) {
        if (!(StringUtils.isNotBlank(request.getParentId()) && shopCategoryRepository.existsById(request.getParentId()))) {
            throw ResponseStatusExceptionHelper.badRequest("parent category %s does not exist", request.getParentId());
        }

        return shopCategoryRepository.save(ShopCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .image(request.getImage())
                .parentId(request.getParentId())
                .build()
        ).toResponse();
    }

    @Override
    @CacheEvict(value = "shop-category", allEntries = true)
    public void remove(String shopCategoryId) {
        if (!shopCategoryRepository.existsById(shopCategoryId)) {
            throw ResponseStatusExceptionHelper.badRequest("shop category %s does not exist", shopCategoryId);
        }
        shopCategoryRepository.deleteById(shopCategoryId);
    }

    @Override
    @CacheEvict(value = "shop-category", allEntries = true)
    public ShopCategoryResponse update(String shopCategoryId, UpdateShopCategoryRequest request) {
        if (!(StringUtils.isNotBlank(request.getParentId()) && shopCategoryRepository.existsById(request.getParentId()))) {
            throw ResponseStatusExceptionHelper.badRequest("parent category %s does not exist", request.getParentId());
        }
        ShopCategory shopCategory = shopCategoryRepository.findById(shopCategoryId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("shop category %s does not exist", shopCategoryId));
        shopCategory.setName(request.getName())
                .setDescription(request.getDescription())
                .setImage(request.getImage())
                .setParentId(request.getParentId());
        return shopCategoryRepository.save(shopCategory).toResponse();

    }
}
