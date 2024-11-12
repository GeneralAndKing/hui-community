package cn.hui_community.service.enums;

public enum ClientEnum {
    WECHAT_MINI_PROGRAM("wechat_mini_program"),
    BROWSER("browser");
    private final String value;

    ClientEnum(String value) {
        this.value = value;
    }


}
