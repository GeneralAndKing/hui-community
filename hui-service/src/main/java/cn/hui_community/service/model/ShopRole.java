package cn.hui_community.service.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_shop_role")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ShopRole extends Base {
    private String name;

    @ManyToMany
    @JoinTable(
            name = "h_shop_role_permission_mapping",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private Set<ShopRoleMapping> mappings;

    private String description;


}
