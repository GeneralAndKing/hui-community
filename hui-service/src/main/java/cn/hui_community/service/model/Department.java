package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.DepartmentResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "deparment")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Department extends Base {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_id", insertable = false, updatable = false)
    private String communityId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Department parent;
    @Column(name = "parent_id", updatable = false, insertable = false)
    private String parentId;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Department> children;


    public DepartmentResponse toResponse() {
        return DepartmentResponse.builder()
                .name(this.getName())
                .communityId(this.getCommunityId())
                .parentId(this.getParentId())
                .build();
    }
}
