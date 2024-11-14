package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.BaseResponse;
import cn.hui_community.service.model.dto.SysPermissionResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_sys_permission")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysPermission extends Base implements GrantedAuthority {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public SysPermissionResponse toResponse() {
        return SysPermissionResponse
                .builder()
                .id(getId())
                .createBy(getCreateBy())
                .createTime(getCreateTime())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .name(getName())
                .description(getDescription())
                .build();
    }
}
