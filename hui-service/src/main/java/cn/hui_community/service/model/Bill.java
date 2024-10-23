package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Bill extends Base {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SysUser user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;


    @Column(name = "payment_id", updatable = false, insertable = false)
    private String paymentId;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "completed_time")
    private Instant completedTime;

    @Column(name = "order_id")
    private String orderId;


}
