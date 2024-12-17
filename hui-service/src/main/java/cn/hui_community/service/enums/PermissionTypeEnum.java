package cn.hui_community.service.enums;

import lombok.Getter;

@Getter
public enum PermissionTypeEnum {

    SYS("SYS"),
    USER("USER"),
    SHOP("SHOP"),
    SHOPKEEPER("SHOPKEEPER");
    private final String value;

    PermissionTypeEnum(String value) {
        this.value = value;
    }


}
