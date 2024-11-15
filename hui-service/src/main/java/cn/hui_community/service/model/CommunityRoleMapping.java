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
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_community_community_role_mapping")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class CommunityRoleMapping extends Base {

    @JoinColumn(name = "community_id")
    @ManyToOne
    private Community community;

    @JoinColumn(name = "role_id")
    @ManyToOne
    private CommunityRole role;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;

    @Column(name = "community_id", insertable = false, updatable = false)
    private String communityId;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @Column(name = "locked_time")
    private Instant lockedTime;
}
