package cn.hui_community.service.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class RolesRequest {
    private Set<String> roleIds;
}
