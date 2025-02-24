package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.model.ProductCategory;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
public class ProductResponse extends BaseResponse {

    private String shopId;

    private ProductCategoryResponse category;

    private String name;

    private String description;

    private String image;


    private BigDecimal price;
}
