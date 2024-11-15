package cn.hui_community.service.configuration.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;

@Setter
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

  private String realmName;

  /**
   * Collect error details from the provided parameters and format according to RFC
   * 6750, specifically {@code error}, {@code error_description}, {@code error_uri}, and
   * {@code scope}.
   *
   * @param request               that resulted in an <code>AccessDeniedException</code>
   * @param response              so that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    StringBuilder description = new StringBuilder();
    if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
      description.append(BearerTokenErrorCodes.INSUFFICIENT_SCOPE + ":The request requires higher privileges than provided by the access token.");
    }
    if (this.realmName != null) {
      description.append("realm=").append(realmName);
    }
    // TODO: 令牌验证失败的错误响应
//    buildResponse(response, HttpStatus.FORBIDDEN,
//        new ErrorResponse(description.toString(), accessDeniedException));
  }

}
