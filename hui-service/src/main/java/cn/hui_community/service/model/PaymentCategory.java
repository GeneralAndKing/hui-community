package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.PaymentCategoryResponse;
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
@Table(name = "h_payment_category")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class PaymentCategory extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;


    public PaymentCategoryResponse toResponse() {
        return PaymentCategoryResponse.builder()
                .id(this.getId())
                .name(this.getName())
                .icon(this.getIcon())
                .build();
    }
}
