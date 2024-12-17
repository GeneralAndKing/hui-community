package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.ShopDetailResponse;
import cn.hui_community.service.model.dto.response.ShopSysShowResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "area_id", insertable = false, updatable = false)
    private String areaId;

    @Column(name = "facede_img")
    private String facadeImg;

    @ElementCollection
    @CollectionTable(name = "h_shop_business_img", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "business_img")
    private List<String> businessImg;

    @Column(name = "image")
    private String image;

    @Column(name = "notice")
    private String notice;


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

    public ShopSysShowResponse toSysShowResponse() {
        return ShopSysShowResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .name(getName())
                .address(getAddress())
                .phone(getPhone())
                .facadeImg(getFacadeImg())
                .image(getImage())
                .notice(getNotice())
                .longitude(getLongitude())
                .latitude(getLatitude())
                .areaId(getAreaId())
                .categories(getCategories().stream().map(ShopCategory::toResponse).collect(Collectors.toSet()))
                .build();
    }

    public ShopDetailResponse toDetailResponse() {
        return ShopDetailResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .name(getName())
                .address(getAddress())
                .phone(getPhone())
                .facadeImg(getFacadeImg())
                .image(getImage())
                .notice(getNotice())
                .longitude(getLongitude())
                .latitude(getLatitude())
                .areaId(getAreaId())
                .categories(getCategories().stream().map(ShopCategory::toResponse).collect(Collectors.toSet()))
                .owner(getOwner().toResponse())
                .businessImg(getBusinessImg())
                .roles(getRoles().stream().map(mapping -> mapping.getRole().toResponse()).collect(Collectors.toSet()))
                .communities(getCommunityShopMappings().stream().map(mapping -> mapping.getCommunity().toResponse()).collect(Collectors.toSet()))
                .build();
    }
}
