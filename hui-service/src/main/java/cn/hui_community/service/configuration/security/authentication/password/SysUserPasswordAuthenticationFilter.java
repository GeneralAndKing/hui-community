package cn.hui_community.service.configuration.security.authentication.password;

import cn.hui_community.service.configuration.security.authentication.*;
import cn.hui_community.service.helper.SpringBeanHelper;
import cn.hui_community.service.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class SysUserPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    record Request(String username, String password) {
    }

    private final ObjectMapper objectMapper;

    public SysUserPasswordAuthenticationFilter(ObjectMapper objectMapper,
                                               AuthenticationManager authenticationManager,
                                               GlobalAuthenticationDetailsSource globalAuthenticationDetailsSource,
                                               GlobalAuthenticationFailureHandler globalAuthenticationFailureHandler,
                                               GlobalAuthenticationSuccessHandler globalAuthenticationSuccessHandler
    ) {
        super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()), authenticationManager);

        setAuthenticationDetailsSource(globalAuthenticationDetailsSource);
        setAuthenticationSuccessHandler(globalAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(globalAuthenticationFailureHandler);
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        Request requestJsonBody = objectMapper.readValue(requestBody, Request.class);
//        GlobalWebAuthenticationDetails details = (GlobalWebAuthenticationDetails) authenticationDetailsSource.buildDetails(
//                request);
        SysUserPasswordAuthenticationToken sysUserPasswordAuthenticationToken = SysUserPasswordAuthenticationToken.unauthenticated(requestJsonBody.username(), requestJsonBody.password());

        return getAuthenticationManager().authenticate(sysUserPasswordAuthenticationToken);
    }
}
