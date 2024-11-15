package cn.hui_community.service.configuration.security.authentication;

import cn.hui_community.service.configuration.security.Token;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GlobalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        log.info("Authentication success");
        Object details = authentication.getDetails();
        //type Must be GlobalWebAuthenticationDetails
        GlobalWebAuthenticationDetails detail = (GlobalWebAuthenticationDetails) details;
        //TODO 当前只有sys user
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        Token token = authenticationService.buildTokenFromSysUser(sysUser);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String responseBody = objectMapper.writeValueAsString(token);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(responseBody);
        }
    }
}
