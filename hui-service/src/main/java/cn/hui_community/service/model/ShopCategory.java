package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ShopCategory parent;
    @Column(name = "parent_id", updatable = false, insertable = false)
    private String parentId;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShopCategory> children;

    @Column(name = "level")
    private Integer level;

    @ManyToMany
    private Set<Shop> shops;
}
