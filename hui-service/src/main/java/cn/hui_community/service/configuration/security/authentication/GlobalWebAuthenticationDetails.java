package cn.hui_community.service.configuration.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Setter
@Getter
public class GlobalWebAuthenticationDetails extends WebAuthenticationDetails {

    private String remoteAddress;

    private String sessionId;

    public GlobalWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }

    public GlobalWebAuthenticationDetails(String remoteAddress, String sessionId) {
        super(remoteAddress, sessionId);
    }
}
