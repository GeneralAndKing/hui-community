package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.ShopShowResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_shop")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Shop extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "area_id", insertable = false, updatable = false)
    private String areaId;


    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Shopkeeper owner;

    @Column(name = "owner_id", insertable = false, updatable = false)
    private String ownerId;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<ShopRoleMapping> roles;

    @ManyToMany
    @JoinTable(
            name = "h_shop_shop_category_mapping",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_category_id")
    )
    private Set<ShopCategory> categories;


    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private Set<CommunityShopMapping> communityShopMappings;

    public ShopShowResponse toShowResponse() {
    return null;
    }
}
