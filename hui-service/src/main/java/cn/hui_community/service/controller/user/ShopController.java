package cn.hui_community.service.controller.user;

import cn.hui_community.service.helper.ResponseStatusExceptionHelper;
import cn.hui_community.service.model.dto.ShopShowResponse;
import cn.hui_community.service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping("")
    public Page<ShopShowResponse> page(@RequestParam Pageable pageable, @RequestParam(required = false) String communityId,
                                       @RequestParam(required = false) String categoryId,
                                       @RequestParam(required = false) Float longitude, @RequestParam(required = false) Float latitude) {
        return shopService.pageByParams(communityId, categoryId, longitude, latitude, pageable);
    }
}
