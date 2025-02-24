package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.model.Shop;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProductCategoryResponse extends BaseResponse {
    private String name;

    private String description;

    private Integer sort;

    private String shopId;
}
