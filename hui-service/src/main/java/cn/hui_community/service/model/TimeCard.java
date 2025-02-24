package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_time_card")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class TimeCard extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private CardTemplate template;

    @Column(name = "template_id", insertable = false, updatable = false)
    private String templateId;

    @Column(name = "expired_time")
    private Instant expiredTime;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;
}
