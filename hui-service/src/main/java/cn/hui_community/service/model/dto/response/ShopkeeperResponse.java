package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ShopkeeperResponse extends BaseResponse {
    private String name;
    private String description;

    private String openId;
}
