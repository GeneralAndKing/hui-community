package cn.hui_community.service.controller.admin;

import cn.hui_community.service.model.dto.AddCommunityRequest;
import cn.hui_community.service.model.dto.CommunityResponse;
import cn.hui_community.service.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
