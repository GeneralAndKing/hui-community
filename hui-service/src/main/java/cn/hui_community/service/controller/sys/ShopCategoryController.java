package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.request.AddShopCategoryRequest;
import cn.hui_community.service.model.dto.request.UpdateShopCategoryRequest;
import cn.hui_community.service.model.dto.response.ShopCategoryResponse;
import cn.hui_community.service.service.ShopCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "sys-shop-category-controller")
@RequiredArgsConstructor
@RequestMapping("/shop-category")
public class ShopCategoryController {
    private final ShopCategoryService shopCategoryService;

    @GetMapping("/page")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public Page<ShopCategoryResponse> page(@RequestParam(required = false) String parentId, @RequestParam(required = false) String likedName, Pageable pageable) {
        return shopCategoryService.pageByParams(parentId, likedName, pageable);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public ShopCategoryResponse add(@RequestBody AddShopCategoryRequest request) {
        return shopCategoryService.add(request);
    }

    @DeleteMapping("/{shopCategoryId}")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public void remove(@PathVariable String shopCategoryId) {
        shopCategoryService.remove(shopCategoryId);
    }

    @PutMapping("/{shopCategoryId}")
    @PreAuthorize("hasAuthority(@auth.SUPER_AUTHORITY_PREFIX+'001')")
    public ShopCategoryResponse update(@PathVariable String shopCategoryId, @RequestBody UpdateShopCategoryRequest request) {
        return shopCategoryService.update(shopCategoryId, request);
    }
}
