package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
public class ShopRoleMappingResponse extends BaseResponse {

    private String roleId;


    private String shopId;

    private Instant expiredTime;
}
