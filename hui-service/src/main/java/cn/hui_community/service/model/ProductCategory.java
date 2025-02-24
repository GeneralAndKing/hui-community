package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.ProductCategoryResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_product_category")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory extends Base {


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "sort")
    private Integer sort;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "shop_id", insertable = false, updatable = false)
    private String shopId;

    public ProductCategoryResponse toResponse() {
        return ProductCategoryResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .name(getName())
                .description(getDescription())
                .sort(getSort())
                .shopId(getShopId())
                .build();
    }
}
