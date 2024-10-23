package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "employee_permission")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class EmployeePermission extends Base {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
