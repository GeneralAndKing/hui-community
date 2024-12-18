package cn.hui_community.service.helper;

import cn.hui_community.service.enums.PermissionTypeEnum;
import cn.hui_community.service.enums.ShopRoleEnum;
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

    public static final String DEPOSIT_PERMISSION_NAME = "DEPOSIT";

    public static final String SCORE_PERMISSION_NAME = "SCORE";

    public static final String TIME_PERMISSION_NAME = "TIME";

    public static final String DISPATCH_PERMISSION_NAME = "DISPATCH";


    private static final List<Triple<String, String, String>> shopPermissions = List.of(
            Triple.of(DEPOSIT_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The deposit permission is represent whether the shop has a deposit card."),
            Triple.of(SCORE_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The score permission is represent whether the shop has a score card."),
            Triple.of(TIME_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The time permission is represent whether the shop has a time card."),
            Triple.of(DISPATCH_PERMISSION_NAME, PermissionTypeEnum.SHOP.getValue(), "The shop dispatch permission is represent whether the shop has show dispatch.")
    );

    private static final Map<String, Permission> permissionMap = new HashMap<>();
    private static final Map<ShopRoleEnum, ShopRole> shopRoleMap = new HashMap<>();
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
        for (ShopRoleEnum shopRoleEnum : ShopRoleEnum.values()) {
            ShopRole shopRole = shopRoleRepository.findByName(shopRoleEnum.getName()).or(() -> Optional.of(
                    shopRoleRepository.save(ShopRole.builder()
                            .name(shopRoleEnum.getName())
                            .permissions(shopRoleEnum.getPermissions().stream().map(permissionMap::get).collect(Collectors.toSet()))
                            .description(shopRoleEnum.getDescription())
                            .build()
                    )
            )).get();
            shopRoleMap.put(shopRoleEnum, shopRole);
        }
    }

    public static ShopRole fromEnum(ShopRoleEnum shopRoleEnum) {
        return shopRoleMap.get(shopRoleEnum);
    }
}
