package cn.hui_community.service.helper;

import cn.hui_community.service.enums.PermissionTypeEnum;
import cn.hui_community.service.model.Permission;
import cn.hui_community.service.model.ShopRole;
import cn.hui_community.service.repository.*;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component(value = "shop-role")
public class ShopRoleHelper {
    public static final String DEPOSIT_ROLE_NAME = "储蓄卡";
    public static final String DEPOSIT_PERMISSION_NAME = "DEPOSIT";


    public static final String SCORE_ROLE_NAME = "积分卡";
    public static final String SCORE_PERMISSION_NAME = "SCORE";


    public static final String TIME_ROLE_NAME = "时效卡";
    public static final String TIME_PERMISSION_NAME = "TIME";

    public static final String SHOP_DISPATCH_ROLE_NAME = "商家自配";
    public static final String SHOP_DISPATCH_PERMISSION_NAME = "SHOW_DISPATCH";

    public static final String SYS_USER_DISPATCH_ROLE_NAME = "物业配送";
    public static final String SYS_USER_DISPATCH_PERMISSION_NAME = "SYS_DISPATCH_DISPATCH";

    private static final List<Triple<String, String, String>> shopPermissions = List.of(
            Triple.of(DEPOSIT_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The deposit permission is represent whether the shop has a deposit card."),
            Triple.of(SCORE_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The score permission is represent whether the shop has a score card."),
            Triple.of(TIME_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The time permission is represent whether the shop has a time card."),
            Triple.of(SHOP_DISPATCH_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The shop dispatch permission is represent whether the shop has show dispatch."),
            Triple.of(SYS_USER_DISPATCH_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The sys user dispatch permission is represent whether the shop has sys user dispatch.")
    );

    private static final List<Triple<String, List<String>, String>> assignedShopRoles = List.of(
            Triple.of(SCORE_ROLE_NAME, List.of(SCORE_PERMISSION_NAME), "商家积分卡功能"),
            Triple.of(TIME_ROLE_NAME, List.of(TIME_PERMISSION_NAME), "商家时效卡功能"),
            Triple.of(DEPOSIT_ROLE_NAME, List.of(DEPOSIT_PERMISSION_NAME), "商铺储蓄卡功能"),
            Triple.of(SHOP_DISPATCH_ROLE_NAME, List.of(SHOP_DISPATCH_PERMISSION_NAME), "商家自配功能"),
            Triple.of(SYS_USER_DISPATCH_ROLE_NAME, List.of(SYS_USER_DISPATCH_PERMISSION_NAME), "物业配送功能")
    );

    private static final Map<String, Permission> permissionMap = new HashMap<>();
    private static final Map<String, ShopRole> shopRoleMap = new HashMap<>();
    private static PermissionRepository permissionRepository;
    private static ShopRoleRepository shopRoleRepository;

    private ShopRoleHelper(PermissionRepository permissionRepository,
                           ShopRoleRepository shopRoleRepository) {
        ShopRoleHelper.permissionRepository = permissionRepository;
        ShopRoleHelper.shopRoleRepository = shopRoleRepository;
        for (Triple<String, String, String> permissionTriple : shopPermissions) {
            Permission permission = permissionRepository.findByName(permissionTriple.getLeft()).or(() -> Optional.of(
                    permissionRepository.save(Permission.builder()
                            .name(permissionTriple.getLeft())
                            .type(permissionTriple.getMiddle())
                            .description(permissionTriple.getRight())
                            .build()
                    ))).get();
            permissionMap.put(permissionTriple.getLeft(), permission);
        }
        for (Triple<String, List<String>, String> shopRoleTriple : assignedShopRoles) {
            ShopRole shopRole = shopRoleRepository.findByName(shopRoleTriple.getLeft()).or(() -> Optional.of(
                    shopRoleRepository.save(ShopRole.builder()
                            .name(shopRoleTriple.getLeft())
                            .permissions(shopRoleTriple.getMiddle().stream().map(permissionMap::get).collect(Collectors.toSet()))
                            .description(shopRoleTriple.getRight())
                            .build()
                    )
            )).get();
            shopRoleMap.put(shopRoleTriple.getLeft(), shopRole);
        }
    }


    public static ShopRole depositRole() {
        return shopRoleMap.get(DEPOSIT_ROLE_NAME);
    }

    public static ShopRole scoreRole() {
        return shopRoleMap.get(SCORE_ROLE_NAME);
    }

    public static ShopRole timeRole() {
        return shopRoleMap.get(TIME_ROLE_NAME);
    }

    public static ShopRole shopDispatchRole() {
        return shopRoleMap.get(SHOP_DISPATCH_ROLE_NAME);
    }

    public static ShopRole sysUserDispatchRole() {
        return shopRoleMap.get(SYS_USER_DISPATCH_ROLE_NAME);
    }
}
