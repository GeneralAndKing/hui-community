package cn.hui_community.service.configuration.security.authentication.wechat;

import cn.hui_community.service.configuration.security.GlobalAuthHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class UserWechatMiniAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public record Request(String code, String username) {
    }

    private final ObjectMapper objectMapper;


    public UserWechatMiniAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/user-api/login", HttpMethod.POST.name()), authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        Request requestJsonBody = objectMapper.readValue(requestBody, Request.class);
        UserWechatMiniTokenAuthenticationToken refreshTokenAuthenticationToken = UserWechatMiniTokenAuthenticationToken.unauthenticated(requestJsonBody.username(), requestJsonBody.code());
        return getAuthenticationManager().authenticate(refreshTokenAuthenticationToken);
    }
}
