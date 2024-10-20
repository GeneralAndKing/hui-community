package cn.hui_community.service.controller.admin;

import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.dto.AreaResponse;
import cn.hui_community.service.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
    private final AreaRepository areaRepository;

    @GetMapping("")
    public List<AreaResponse> all() {
        return areaRepository.findAll().stream().map(Area::toResponse).toList();
    }
}
