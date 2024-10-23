package cn.hui_community.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class Payment extends Base {

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_id", updatable = false,insertable=false)
    private String communityId;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "stop_time")
    private Instant stopTime;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
