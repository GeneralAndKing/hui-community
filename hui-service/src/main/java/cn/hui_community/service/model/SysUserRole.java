package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.SysUserRoleResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
@Table(name = "h_sys_user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "community_id"})})
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


    public Set<GrantedAuthority> generateAuthorities() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getAuthority() + "_" + getCommunityId()))
                .collect(Collectors.toSet());
    }

    public SysUserRoleResponse toResponse() {
        return SysUserRoleResponse
                .builder()
                .id(getId())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .createTime(getCreateTime())
                .updateTime(getUpdateTime())
                .communityName(getCommunity().getName())
                .communityId(getCommunityId())
                .description(getDescription())
                .permissions(getPermissions().stream().map(Permission::toResponse).collect(Collectors.toSet()))
                .name(getName())
                .build();
    }


}
