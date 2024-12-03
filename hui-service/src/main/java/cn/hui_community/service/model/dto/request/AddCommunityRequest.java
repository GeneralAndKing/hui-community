package cn.hui_community.service.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddCommunityRequest {

    private String code;

    private String name;

    private String address;

    private String areaId;

    private Float longitude;

    private Float latitude;
}
