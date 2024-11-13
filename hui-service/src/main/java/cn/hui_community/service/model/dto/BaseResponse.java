package cn.hui_community.service.model.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
public class BaseResponse {

    protected String id;

    protected String createBy;

    protected String updateBy;


    protected Instant updateTime;

    protected Instant createTime;

}
