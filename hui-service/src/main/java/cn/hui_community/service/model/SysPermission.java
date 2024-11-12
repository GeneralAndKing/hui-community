package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_sys_permission")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class SysPermission extends Base implements GrantedAuthority {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
