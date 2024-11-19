package cn.hui_community.service.component;

import cn.hui_community.service.configuration.WechatMiniConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WechatMiniClient {
    @SuppressWarnings("SpellCheckingInspection")
    record AuthResponse(
            @JsonProperty("errcode")
            Integer errorCode,
            @JsonProperty("errmsg")
            String errorMessage,
            String openid,
            @JsonProperty("unionid")
            String unionId,
            @JsonProperty("session_key")
            String sessionKey) {
    }

    private final WechatMiniConfiguration.Properties properties;

    private final RestTemplate wechatRestTemplate;

    private final static String BASE_URL = "https://api.weixin.qq.com";

    private final static String LOGIN_URL = BASE_URL + "/sns/jscode2session";


    public static String formatErrorCode(Integer errorCode) {
        return errorCode + switch (errorCode) {
            case -1 -> "系统繁忙，请稍候再试";
            case 0 -> "请求成功";
            case 40001 -> "AppSecret错误或者AppSecret不属于这个应用，请开发者确认AppSecret的正确性";
            case 40002 -> "请确保grant_type字段值为client_credential";
            case 40013 -> "不合法的 AppID ，请开发者检查 AppID 的正确性，避免异常字符，注意大小写";
            case 40029 -> "js_code无效";
            case 45011 -> "API 调用太频繁，请稍候再试";
            // https://developers.weixin.qq.com/miniprogram/dev/framework/operation.html
            case 40226 -> "高风险等级用户，小程序登录拦截 。风险等级详见用户安全解方案";
            case 40164 -> "调用接口的IP地址不在白名单中，请在接口IP白名单中进行设置。";
            case 89503 -> "此IP调用需要管理员确认,请联系管理员";
            case 89501 -> "此IP正在等待管理员确认,请联系管理员";
            case 89506 -> "24小时内该IP被管理员拒绝调用两次，24小时内不可再使用该IP调用";
            case 89507 -> "1小时内该IP被管理员拒绝调用一次，1小时内不可再使用该IP调用";
            default -> "未知错误";
        };
    }

    public String authUserOpenId(String code) {
        String targetUri = UriComponentsBuilder.fromHttpUrl(LOGIN_URL)
                .queryParam("appid", properties.getUser().getAppId())
                .queryParam("secret", properties.getShopkeeper().getSecret())
                .queryParam("js_code", code)
                .queryParam("grant_type", "authorization_code")
                .toUriString();
        AuthResponse authResponse = wechatRestTemplate.getForObject(targetUri, AuthResponse.class);
        if (Objects.isNull(authResponse)) {
            throw new BadCredentialsException("wechat mini auth failed");
        }
        if (StringUtils.isEmpty(authResponse.openid())) {
            throw new BadCredentialsException("wechat mini auth failed: %s".formatted(formatErrorCode(authResponse.errorCode())));
        }
        return authResponse.openid();
    }
}
