package cn.hui_community.service.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddShopCategoryRequest {
    private String name;

    private String image;

    private String description;

    private String parentId;
}
