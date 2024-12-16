package cn.hui_community.service.service.impl;

import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.dto.response.AreaResponse;
import cn.hui_community.service.repository.AreaRepository;
import cn.hui_community.service.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;

    @Override
    @Cacheable(value = "areas", key = "T(String).valueOf(#all == true ? 'true' : (#parentId ?: 'default'))")
    public List<AreaResponse> queryByParams(String parentId, Boolean all) {
        if (all != null && all) {
            return areaRepository.findAll().stream().map(Area::toResponse).collect(Collectors.toList());
        }
        return areaRepository.findAllByParentId(parentId).stream().map(Area::toResponse).collect(Collectors.toList());    }
}
