package cn.hui_community.service.controller;

import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.dto.response.AreaResponse;
import cn.hui_community.service.repository.AreaRepository;
import cn.hui_community.service.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping("")
    public List<AreaResponse> all(@RequestParam(required = false) String parentId, @RequestParam(required = false) Boolean all) {
        return areaService.queryByParams(parentId, all);
    }
}
