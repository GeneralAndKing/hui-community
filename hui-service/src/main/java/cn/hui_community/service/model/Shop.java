package cn.hui_community.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
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
@Table(name = "business")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Shop extends Base {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;


    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;
}
