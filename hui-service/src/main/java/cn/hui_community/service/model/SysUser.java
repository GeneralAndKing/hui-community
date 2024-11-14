package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.SysUserResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;
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
@Table(name = "h_sys_user")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysUser extends Base {

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "locked")
    private Boolean locked;

    @ManyToMany
    @JoinTable(
            name = "h_sys_user_role_mapping",
            joinColumns = @JoinColumn(name = "sys_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Exclude
    private Set<SysRole> roles;

    public SysUserResponse toResponse() {
        return SysUserResponse.builder()
                .id(this.getId())
                .createTime(this.getCreateTime())
                .createBy(this.getCreateBy())
                .updateBy(this.getUpdateBy())
                .updateTime(this.getUpdateTime())
                .password("****************")
                .phone(this.getPhone())
                .locked(this.getLocked())
                .displayName(this.getDisplayName())
                .username(this.getUsername())
                .build();
    }
}
