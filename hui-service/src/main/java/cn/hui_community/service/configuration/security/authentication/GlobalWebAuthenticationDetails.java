package cn.hui_community.service.configuration.security.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class GlobalWebAuthenticationDetails extends WebAuthenticationDetails {

  public GlobalWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
  }

  public GlobalWebAuthenticationDetails(String remoteAddress, String sessionId) {
    super(remoteAddress, sessionId);
  }
}
