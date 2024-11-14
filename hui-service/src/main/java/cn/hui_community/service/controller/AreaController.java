package cn.hui_community.service.controller;

import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.dto.AreaResponse;
import cn.hui_community.service.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
  @PreAuthorize("hasAuthority('SUPER')")
  public List<AreaResponse> all() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    return areaRepository.findAll().stream().map(Area::toResponse).toList();
  }
}
