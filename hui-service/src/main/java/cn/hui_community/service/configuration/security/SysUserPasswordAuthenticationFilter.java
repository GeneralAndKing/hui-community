package cn.hui_community.service.configuration.security;

import cn.hui_community.service.model.dto.LoginSysUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SysUserPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private static final AntPathRequestMatcher MATCHER = new AntPathRequestMatcher("/login", HttpMethod.POST.name());
    private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public SysUserPasswordAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(MATCHER, authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!MATCHER.matches(request)) {
            throw new AuthenticationServiceException("Method not supported: " + request.getMethod());
        }
        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        LoginSysUserRequest loginSysUserRequest = objectMapper.readValue(requestBody, LoginSysUserRequest.class);
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(loginSysUserRequest.getUsername(), loginSysUserRequest.getPassword());
        unauthenticated.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(unauthenticated);
    }
}
