package cn.hui_community.service.model;


import cn.hui_community.service.model.dto.response.ProductResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_product")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Product extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;


    @Column(name = "shop_id", insertable = false, updatable = false)
    private String shopId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private String categoryId;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private BigDecimal price;

    public ProductResponse toResponse() {
        return ProductResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .name(getName())
                .description(getDescription())
                .image(getImage())
                .price(getPrice())
                .shopId(getShopId())
                .category(getCategory().toResponse())
                .build();
    }
}
