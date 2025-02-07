package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.ShopCategoryResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_shop_category")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ShopCategory extends Base {


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ShopCategory parent;
    @Column(name = "parent_id", updatable = false, insertable = false)
    private String parentId;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShopCategory> children;

    @ManyToMany
    private Set<Shop> shops;

    public ShopCategoryResponse toResponse() {
        ShopCategory parent = getParent();
        return ShopCategoryResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .name(getName())
                .description(getDescription())
                .image(getImage())
                .parentId(getParentId())
                .parentName(parent!=null?parent.getName():null)
                .build();
    }
}
