package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.response.AreaResponse;

import java.util.List;

public interface AreaService {

    List<AreaResponse> queryByParams(String parentId, Boolean all);
}
