package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.CommunityShopMapping;
import cn.hui_community.service.model.Shop;
import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.model.dto.response.ShopSysShowResponse;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.CommunityShopMappingRepository;
import cn.hui_community.service.repository.ShopRepository;
import cn.hui_community.service.service.ShopService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final CommunityRepository communityRepository;
    private final CommunityShopMappingRepository communityShopMappingRepository;


    @Override
    public Page<ShopSysShowResponse> pageByParams(String communityId, List<String> categoryIds, String likedName, Pageable pageable) {
        Page<CommunityShopMapping> communityShopMappingPage = communityShopMappingRepository.findAll((root, query, criteriaBuilder) -> {
            Join<CommunityShopMapping, Shop> shopJoin = root.join("shop");
            List<Predicate> predicates = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(categoryIds)) {
                Predicate predicate = criteriaBuilder.in(shopJoin.get("categoryId")).in(categoryIds);
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(communityId)) {
                Predicate communityPredicate = criteriaBuilder.equal(root.get("communityId"), communityId);
                predicates.add(communityPredicate);
            }
            if (StringUtils.isNotBlank(likedName)) {
                Predicate namePredicate = criteriaBuilder.like(shopJoin.get("name"), "%" + likedName + "%");
                predicates.add(namePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return PageHelper.map(communityShopMappingPage, communityShopMapping -> communityShopMapping.getShop().toSysShowResponse());
    }

    @Override
    public ShopDetailResponse detail(String shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(ResponseStatusExceptionHelper.badRequestSupplier("shop %s does not exist", shopId));
        return shop.toDetailResponse();
    }

}
