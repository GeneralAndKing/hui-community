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
@Table(name = "h_sys_role")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysRole extends Base {

    private String name;
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_id", updatable = false, insertable = false)
    private String communityId;

    @ManyToMany
    @JoinTable(
            name = "h_sys_role_permission_mapping",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<SysPermission> permissions;

    private String description;

}
