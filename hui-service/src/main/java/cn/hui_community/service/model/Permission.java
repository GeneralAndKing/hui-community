package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.PermissionResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_permission", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "type"})})
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Base implements GrantedAuthority {

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return getType() + "_" + getName();
    }

    public PermissionResponse toResponse() {
        return PermissionResponse
                .builder()
                .id(getId())
                .createBy(getCreateBy())
                .createTime(getCreateTime())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .name(getName())
                .type(getType())
                .description(getDescription())
                .build();
    }
}
