package cn.hui_community.service.model;

import cn.hui_community.service.enums.CardEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_card_template")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class CardTemplate extends Base {

    @ManyToOne
    @JoinColumn(name = "shopkeeper_id")
    private Shopkeeper shopkeeper;

    @Column(name = "shopkeeper_id", insertable = false, updatable = false)
    private String shopkeeperId;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private CardEnum type;

    @Column(name = "unit")
    private String unit;

    @Column(name = "image")
    private String image;

}
