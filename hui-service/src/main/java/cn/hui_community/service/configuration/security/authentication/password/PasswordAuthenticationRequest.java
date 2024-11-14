package cn.hui_community.service.configuration.security.authentication.password;


public record PasswordAuthenticationRequest(
    String username,
    String password
) {
}
