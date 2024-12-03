package cn.hui_community.service.service.impl;

import cn.hui_community.service.helper.PageHelper;
import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.dto.response.ShopShowResponse;
import cn.hui_community.service.repository.CommunityRepository;
import cn.hui_community.service.repository.CommunityShopMappingRepository;
import cn.hui_community.service.repository.ShopRepository;
import cn.hui_community.service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final CommunityRepository communityRepository;
    private final CommunityShopMappingRepository communityShopMappingRepository;

    @Override
    public Page<ShopShowResponse> pageByParams(String communityId, String categoryId, Float longitude, Float latitude, Pageable pageable) {
        if (StringUtils.isBlank(communityId) && (Float.isNaN(longitude) || Float.isNaN(latitude))) {
            throw ResponseStatusExceptionHelper.badRequest("小区ID和经纬度不能都为空");
        }
//        communityShopMappingRepository.findAll((root, query, criteriaBuilder) -> {
//            if (categoryId!=null){
//                Join<CommunityShopMapping, Shop> shopJoin = root.join("shop");
//                Predicate predicate = criteriaBuilder.equal(shopJoin.get("categoryId"), categoryId);
//            }
//            if(StringUtils.isNotBlank(communityId)){
//                Predicate communityPredicate = criteriaBuilder.equal(root.get("communityId"), communityId);
//            }
//            else{
//                //
//            }
//            return criteriaBuilder.equal(shopJoin.get("categoryId"), categoryId);
//        }, pageable);

        return PageHelper.map(communityShopMappingRepository.findAllByCommunityId(communityId, pageable), mapping -> {
            return mapping.getShop().toShowResponse();
        });
    }

}
