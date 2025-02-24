package cn.hui_community.service.model;

import cn.hui_community.service.enums.CardEnum;
import cn.hui_community.service.model.dto.response.CardTemplateResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_card_template")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class CardTemplate extends Base {
    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @Column(name = "type")
    private CardEnum type;

    @Column(name = "shop_id", insertable = false, updatable = false)
    private String shopId;

    @Column(name = "level")
    private String level;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "image")
    private String image;

    @Column(name = "unit")
    private String unit;

    public CardTemplateResponse toResponse() {
        return CardTemplateResponse.builder()
                .id(getId())
                .createBy(getCreateBy())
                .createTime(getCreateTime())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .type(getType())
                .level(getLevel())
                .shopId(getShopId())
                .sort(getSort())
                .image(getImage())
                .unit(getUnit())
                .build();
    }
}
