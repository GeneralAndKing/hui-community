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
@Table(name = "business_type")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class ShopType extends Base {


    @ManyToOne
    @JoinColumn(name = "primary_type_id")
    private ShopPrimaryType primaryType;

    @Column(name = "primary_type_id", updatable = false)
    private String primaryTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
