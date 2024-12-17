package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.response.ShopkeeperResponse;
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
@Getter
@Setter
@Accessors(chain = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "h_shopkeeper")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Shopkeeper extends Base {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "open_id", unique = true)
    private String openId;


    public ShopkeeperResponse toResponse() {
        return ShopkeeperResponse.builder()
                .id(getId())
                .createTime(getCreateTime())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .name(getName())
                .description(getDescription())
                .openId(getOpenId())
                .build();
    }
}
