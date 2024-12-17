package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.model.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@SuperBuilder
public class ShopDetailResponse extends BaseResponse {
    private String name;

    private String address;

    private String phone;

    private String areaId;

    private String facadeImg;

    private List<String> businessImg;

    private String image;

    private String notice;


    private ShopkeeperResponse owner;


    private Float longitude;

    private Float latitude;

    private Set<ShopRoleResponse> roles;

    private Set<ShopCategoryResponse> categories;


    private Set<CommunityResponse> communities;
}
