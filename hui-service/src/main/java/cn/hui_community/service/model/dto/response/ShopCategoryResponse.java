package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ShopCategoryResponse extends BaseResponse {


    private String name;

    private String image;

    private String description;

    private String parentId;

    private String parentName;
}
