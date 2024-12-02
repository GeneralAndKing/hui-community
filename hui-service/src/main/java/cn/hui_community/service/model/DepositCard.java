package cn.hui_community.service.model;

import cn.hui_community.service.enums.CardEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_deposit_card")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class DepositCard extends Base {
    @ManyToOne(fetch = FetchType.LAZY)
    private CardTemplate template;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
