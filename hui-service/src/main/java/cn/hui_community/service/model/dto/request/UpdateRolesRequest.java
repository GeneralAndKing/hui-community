package cn.hui_community.service.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class UpdateRolesRequest {
    private Set<String> roleIds;
}
