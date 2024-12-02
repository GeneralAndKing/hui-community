package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.CommunityResponse;
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
@Table(name = "h_community")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Community extends Base {
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
    @Column(name = "area_id", insertable = false, updatable = false)
    private String areaId;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private Set<CommunityRoleMapping> roleMappings;


    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private Set<CommunityShopMapping> shopMappings;


    public CommunityResponse toResponse() {
        return CommunityResponse.builder()
                .id(getId())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .createTime(getCreateTime())
                .updateTime(getUpdateTime())
                .code(getCode())
                .name(getName())
                .address(getAddress())
                .areaId(getAreaId())
                .longitude(getLongitude())
                .latitude(getLatitude())
                .build();
    }
}
