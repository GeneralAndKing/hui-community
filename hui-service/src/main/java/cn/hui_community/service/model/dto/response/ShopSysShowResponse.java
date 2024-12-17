package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.model.Area;
import cn.hui_community.service.model.ShopCategory;
import cn.hui_community.service.model.ShopRoleMapping;
import cn.hui_community.service.model.Shopkeeper;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
public class ShopSysShowResponse extends BaseResponse{

    private String name;


    private String address;


    private String phone;



    private String areaId;


    private String facadeImg;


    private String image;


    private String notice;


    private Float longitude;


    private Float latitude;


    private Set<ShopCategoryResponse> categories;
}
