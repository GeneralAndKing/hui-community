package cn.hui_community.service.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_shop_shop_role_mapping")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ShopRoleMapping extends Base {
    @JoinColumn(name = "shop_id")
    @ManyToOne
    private Shop shop;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private ShopRole role;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;

    @Column(name = "shop_id", insertable = false, updatable = false)
    private String shopId;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @Column(name = "locked_time")
    private Instant lockedTime;

}
