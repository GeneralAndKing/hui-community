package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.AddCommunityRequest;
import cn.hui_community.service.model.dto.CommunityResponse;
import cn.hui_community.service.model.dto.UpdateCommunityRequest;

public interface CommunityService {
    CommunityResponse addCommunity(AddCommunityRequest request);

    CommunityResponse updateCommunityById(String communityId, UpdateCommunityRequest request);
}
