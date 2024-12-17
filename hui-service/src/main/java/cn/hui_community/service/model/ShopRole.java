package cn.hui_community.service.model;


import cn.hui_community.service.model.dto.response.ShopRoleResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "h_shop_role")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ShopRole extends Base {

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "h_shop_role_permission_mapping",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<ShopRoleMapping> mappings;

    private String description;


    public ShopRoleResponse toResponse() {
        return ShopRoleResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .name(getName())
                .description(getDescription())
                .permissions(getPermissions().stream().map(Permission::toResponse).collect(Collectors.toSet()))
                .build();
    }
}
