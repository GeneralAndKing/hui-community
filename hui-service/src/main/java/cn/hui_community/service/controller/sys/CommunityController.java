package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.AddCommunityRequest;
import cn.hui_community.service.model.dto.CommunityResponse;
import cn.hui_community.service.model.dto.UpdateCommunityRequest;
import cn.hui_community.service.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('SUPER')")
    public CommunityResponse addCommunity(@RequestBody AddCommunityRequest request) {
        return communityService.addCommunity(request);
    }


    @PutMapping("/{communityId}")
    @PreAuthorize("hasAuthority('SUPER')")
    public CommunityResponse updateCommunityById(@PathVariable String communityId, @RequestBody UpdateCommunityRequest request) {
        return communityService.updateCommunityById(communityId, request);
    }
}
