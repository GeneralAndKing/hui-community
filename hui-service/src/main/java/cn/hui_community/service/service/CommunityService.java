package cn.hui_community.service.service;

import cn.hui_community.service.model.dto.AddCommunityRequest;
import cn.hui_community.service.model.dto.CommunityResponse;

public interface CommunityService {
    CommunityResponse addCommunity(AddCommunityRequest request);
}
