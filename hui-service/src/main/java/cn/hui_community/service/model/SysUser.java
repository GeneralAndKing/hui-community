package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.SysUserResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_sys_user")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysUser extends Base {
    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "locked_time")
    private Instant lockedTime;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "h_sys_user_role_mapping",
            joinColumns = @JoinColumn(name = "sys_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<SysUserRole> roles;

    public SysUserResponse toResponse() {
        return SysUserResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .password("****************")
                .phone(getPhone())
                .lockedTime(getLockedTime())
                .expiredTime(getExpiredTime())
                .displayName(getDisplayName())
                .username(getUsername())
                .build();
    }

    //对于系统用户来说，需要加上小区作为权限名
    public Set<GrantedAuthority> generateAuthorities() {
        return getRoles().stream()
                .flatMap(role -> role.generateAuthorities().stream())
                .collect(Collectors.toSet());
    }

    public Map<String, Object> toTokenInfo() {
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("displayName", getDisplayName());
        tokenInfo.put("phone", getPhone());
        tokenInfo.put("permissions", generateAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        tokenInfo.put("roles", getRoles().stream().map(role -> role.getName() + "_" + role.getCommunityId()).toList());
        return tokenInfo;
    }
}
