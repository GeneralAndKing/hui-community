package cn.hui_community.service.model;

import cn.hui_community.service.enums.CardEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
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
@Table(name = "h_card")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Card extends Base {

    @Column(name = "type")
    private CardEnum type;
}
