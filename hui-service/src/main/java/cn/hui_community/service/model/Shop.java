package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Shop extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Shopkeeper owner;

    @Column(name = "owner_id", insertable = false, updatable = false)
    private String ownerId;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @ManyToMany
    @JoinTable(
            name = "shop_shop_category_mapping",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_category_id")
    )
    private Set<ShopCategory> categories;
}
