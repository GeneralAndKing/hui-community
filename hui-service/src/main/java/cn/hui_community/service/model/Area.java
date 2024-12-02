package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.AreaResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_area")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Area extends Base {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Area parent;
    @Column(name = "parent_id", updatable = false, insertable = false)
    private String parentId;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Area> children;
    @Column(name = "code")
    private String code;
    @Column(name = "level")
    private Integer level;
    @Column(name = "name")
    private String name;
    @Column(name = "pinyin")
    private String pinyin;
    @Column(name = "pinyin_prefix")
    private String pinyinPrefix;

    public AreaResponse toResponse() {
        return AreaResponse.builder()
                .id(this.getId())
                .parentId(this.getParentId())
                .code(this.getCode())
                .name(this.getName())
                .pinyin(this.getPinyin())
                .pinyinPrefix(this.getPinyinPrefix())
                .level(this.getLevel())
                .build();
    }
}
