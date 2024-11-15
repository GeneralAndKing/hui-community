package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.SysUserResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serial;
import java.time.Instant;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "h_sys_user_role_mapping",
            joinColumns = @JoinColumn(name = "sys_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Exclude
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
}
