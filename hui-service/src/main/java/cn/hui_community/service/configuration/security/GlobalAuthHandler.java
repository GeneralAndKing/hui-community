package cn.hui_community.service.configuration.security;

import cn.hui_community.service.configuration.security.authentication.password.SysUserPasswordAuthenticationToken;
import cn.hui_community.service.configuration.security.authentication.token.RefreshTokenAuthenticationToken;
import cn.hui_community.service.configuration.security.authentication.wechat.UserWechatMiniTokenAuthenticationToken;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.Token;
import cn.hui_community.service.model.User;
import cn.hui_community.service.component.TokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalAuthHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler, AuthenticationEntryPoint, AccessDeniedHandler {
    record Body(String message) {
    }

    private final TokenFactory tokenFactory;
    private final ObjectMapper objectMapper;


    private void process(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(new Body(exception.getMessage())));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        process(request, response, exception);

    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        process(request, response, authException);
    }


    private void onSysUserSuccess(HttpServletRequest request,
                                  HttpServletResponse response, SysUser sysUser) throws IOException {
        Token token = tokenFactory.buildFromSysUser(sysUser);
        String responseBody = objectMapper.writeValueAsString(token);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(responseBody);
        }
    }

    private void onUserSuccess(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        Token token = tokenFactory.buildFromUser(user);
        String responseBody = objectMapper.writeValueAsString(token);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(responseBody);
        }
    }


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (authentication instanceof SysUserPasswordAuthenticationToken authenticationToken) {
            SysUser sysUser = (SysUser) authenticationToken.getPrincipal();
            onSysUserSuccess(request, response, sysUser);
        } else if (authentication instanceof RefreshTokenAuthenticationToken) {
            if (authentication.getPrincipal() instanceof SysUser sysUser) {
                onSysUserSuccess(request, response, sysUser);
            } else if (authentication.getPrincipal() instanceof User user) {
                onUserSuccess(request, response, user);
            }
        } else if (authentication instanceof UserWechatMiniTokenAuthenticationToken) {
            User user = (User) authentication.getPrincipal();
            onUserSuccess(request, response, user);
        }
        //other auth method

    }


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        StringBuilder description = new StringBuilder();
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
            description.append(BearerTokenErrorCodes.INSUFFICIENT_SCOPE + ":The request requires higher privileges than provided by the access token.");
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(new Body(description.toString())));
    }
}
