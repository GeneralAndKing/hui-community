package cn.hui_community.service.enums;

import lombok.Getter;

@Getter
public enum CardEnum {
    DEPOSIT("deposit"),
    TIME("time"),
    SCORE("score");
    private final String value;

    CardEnum(String value) {
        this.value = value;
    }
}
