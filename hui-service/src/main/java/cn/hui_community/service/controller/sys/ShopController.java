package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.model.dto.response.ShopSysShowResponse;
import cn.hui_community.service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "sys-shop-controller")
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001') or @auth.hasAuthorityPrefix(@auth.ADMIN_AUTHORITY_PREFIX)")
    public Page<ShopSysShowResponse> page(Pageable pageable, @RequestParam(required = false) String communityId,
                                          @RequestParam(required = false) List<String> categoryIds, @RequestParam(required = false) String likedName) {
        return shopService.pageByParams(communityId, categoryIds, likedName, pageable);
    }

    @GetMapping("/{shopId}")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public ShopDetailResponse detail(@PathVariable String shopId) {
        return shopService.detail(shopId);
    }
}
