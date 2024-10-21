package cn.hui_community.service.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponse {

    private String name;

    private String communityId;

    private String parentId;
}
