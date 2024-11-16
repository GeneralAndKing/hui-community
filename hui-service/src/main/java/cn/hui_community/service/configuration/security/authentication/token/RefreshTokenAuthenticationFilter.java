package cn.hui_community.service.configuration.security.authentication.token;

import cn.hui_community.service.configuration.security.JsonBodyAuthHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    record Request(String refreshToken, String id) {
    }

    private final ObjectMapper objectMapper;


    public RefreshTokenAuthenticationFilter(ObjectMapper objectMapper,AuthenticationManager authenticationManager, JsonBodyAuthHandler jsonBodyAuthHandler) {
        super(new AntPathRequestMatcher("/refresh-token", HttpMethod.POST.name()), authenticationManager);
        setAuthenticationSuccessHandler(jsonBodyAuthHandler);
        setAuthenticationFailureHandler(jsonBodyAuthHandler);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        Request requestJsonBody = objectMapper.readValue(requestBody, Request.class);
        RefreshTokenAuthenticationToken refreshTokenAuthenticationToken = RefreshTokenAuthenticationToken.unauthenticated(requestJsonBody.id(), requestJsonBody.refreshToken());
        return getAuthenticationManager().authenticate(refreshTokenAuthenticationToken);
    }
}
