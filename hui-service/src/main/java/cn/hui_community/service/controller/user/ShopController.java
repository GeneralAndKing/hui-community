package cn.hui_community.service.controller.user;

import cn.hui_community.service.model.dto.response.MenuResponse;
import cn.hui_community.service.model.dto.response.ProductResponse;
import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("/{shopId}")
    public ShopDetailResponse detail(@PathVariable String shopId) {
        return shopService.detail(shopId);
    }

    @GetMapping("/{shopId}/menu")
    public List<MenuResponse> products(@PathVariable String shopId) {
        return shopService.products(shopId);
    }
}
