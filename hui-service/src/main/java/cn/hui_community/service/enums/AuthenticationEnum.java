package cn.hui_community.service.enums;

public enum AuthenticationEnum {
    SYS_USER_PASSWORD("sys_user_password");
    private final String value;

    AuthenticationEnum(String value) {
        this.value = value;
    }
}
