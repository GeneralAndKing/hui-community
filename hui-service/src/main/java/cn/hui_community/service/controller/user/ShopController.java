package cn.hui_community.service.controller.user;

import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.model.dto.response.ShopSysShowResponse;
import cn.hui_community.service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ShopDetailResponse detail(@PathVariable String shopId) {
        return shopService.detail(shopId);
    }
}
