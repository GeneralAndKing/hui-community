package cn.hui_community.service.model.dto.request;

import cn.hui_community.service.enums.ShopRoleEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class UpdateShowRoleRequest {
    private ShopRoleEnum role;
    private Instant expiredTime;
}
