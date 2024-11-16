package cn.hui_community.service.enums;

import lombok.Getter;

@Getter
public enum SubjectEnum {

    SYS("SYS"),
    USER("USER"),
    SHOPKEEPER("SHOPKEEPER");
    private final String value;

    SubjectEnum(String value) {
        this.value = value;
    }


}
