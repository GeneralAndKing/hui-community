package cn.hui_community.service.configuration.security.authentication.token;

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
public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public record Request(String refreshToken, String id) {
    }

    private final ObjectMapper objectMapper;


    public RefreshTokenAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/refresh-token", HttpMethod.POST.name()), authenticationManager);
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
