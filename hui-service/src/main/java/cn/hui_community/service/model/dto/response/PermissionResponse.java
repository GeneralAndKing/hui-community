package cn.hui_community.service.model.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PermissionResponse extends BaseResponse {

    private String name;

    private String type;

    private String description;
}
