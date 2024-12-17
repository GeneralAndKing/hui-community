package cn.hui_community.service.controller;

import cn.hui_community.service.model.dto.response.ShopCategoryResponse;
import cn.hui_community.service.service.ShopCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop-category")
public class ShopCategoryController {
    private final ShopCategoryService shopCategoryService;

    @GetMapping("")
    public List<ShopCategoryResponse> all(@RequestParam(required = false) String parentId, @RequestParam(required = false) Boolean all) {
        return shopCategoryService.queryByParams(parentId, all);
    }
}
