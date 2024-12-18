package cn.hui_community.service.enums;

import cn.hui_community.service.helper.ShopRoleHelper;
import lombok.Getter;

import java.util.List;

@Getter
public enum ShopRoleEnum {
    DEPOSIT(List.of(ShopRoleHelper.DEPOSIT_PERMISSION_NAME), "储蓄卡", "商铺储蓄卡功能"),
    TIME(List.of(ShopRoleHelper.TIME_PERMISSION_NAME), "时效卡", "商家时效卡功能"),
    SCORE(List.of(ShopRoleHelper.SCORE_PERMISSION_NAME), "积分卡", "商家积分卡功能"),
    DISPATCH(List.of(ShopRoleHelper.DISPATCH_PERMISSION_NAME), "配送服务", "商家配送功能");

    private final List<String> permissions;
    private final String name;
    private final String description;

    ShopRoleEnum(List<String> permissions, String name, String description) {
        this.permissions = permissions;
        this.name = name;
        this.description = description;
    }
}
