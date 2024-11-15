package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_sys_user_role")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysUserRole extends Base {

    private String name;
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_id", updatable = false, insertable = false)
    private String communityId;

    @ManyToMany
    @JoinTable(
            name = "h_sys_user_role_permission_mapping",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    private String description;


}
