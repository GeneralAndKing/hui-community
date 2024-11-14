package cn.hui_community.service.model;

import cn.hui_community.service.model.dto.BaseResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Data
@Accessors(chain = true)
@ToString
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public abstract class Base implements Serializable {
    @Id
    @UuidGenerator
    @Column(name = "id")
    protected String id;


    @Column(name = "create_by")
    protected String createBy;

    @Column(name = "update_by")
    protected String updateBy;

    @Column(name = "update_time")
    @LastModifiedDate
    protected Instant updateTime;

    @Column(name = "create_time")
    @CreatedDate
    protected Instant createTime;

    public BaseResponse toBaseResponse() {
        return BaseResponse
                .builder()
                .id(getId())
                .createBy(getCreateBy())
                .updateBy(getUpdateBy())
                .updateTime(getUpdateTime())
                .createTime(getCreateTime())
                .build();
    }

}
