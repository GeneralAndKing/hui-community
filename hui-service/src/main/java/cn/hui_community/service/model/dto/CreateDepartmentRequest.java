package cn.hui_community.service.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateDepartmentRequest {
    @NotNull(message = "name cannot be null")
    private String name;

    private String parentId;
    @NotNull(message = "community cannot be null")
    private String communityId;
}
