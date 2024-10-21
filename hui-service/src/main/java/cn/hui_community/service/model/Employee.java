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
@Table(name = "h_employee")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Employee extends Base {

    @Column(name = "name")
    private String name;


    @ManyToMany
    @JoinTable(
            name = "h_employee_role_mapping",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<EmployeeRole> roles;
}
