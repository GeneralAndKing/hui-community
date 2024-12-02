package cn.hui_community.service.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_community_shop_mapping")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class CommunityShopMapping extends Base {
    @JoinColumn(name = "community_id")
    @ManyToOne
    private Community community;

    @JoinColumn(name = "shop_id")
    @ManyToOne
    private Shop shop;

    @Column(name = "shop_id", insertable = false, updatable = false)
    private String shopId;

    @Column(name = "community_id", insertable = false, updatable = false)
    private String communityId;

    @Column(name = "comment")
    private String comment;
}
