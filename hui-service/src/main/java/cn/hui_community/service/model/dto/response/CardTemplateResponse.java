package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.enums.CardEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CardTemplateResponse extends BaseResponse {
    private CardEnum type;

    private String shopId;

    private String level;

    private Integer sort;

    private String image;

    private String unit;
}
