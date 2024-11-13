package cn.hui_community.service.model.dto;

import cn.hui_community.service.model.Area;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CommunityResponse extends BaseResponse {

    private String code;

    private String name;

    private String address;

    private String areaId;

    private Float longitude;

    private Float latitude;
}
