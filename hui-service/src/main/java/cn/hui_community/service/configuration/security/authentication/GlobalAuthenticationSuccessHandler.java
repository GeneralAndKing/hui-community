package cn.hui_community.service.configuration.security.authentication;

import cn.hui_community.service.configuration.security.UserToken;
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

@Slf4j
@RequiredArgsConstructor
public class GlobalAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final AuthenticationService authenticationService;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) throws IOException, ServletException {
    log.info("Authentication success");
    Object principal = authentication.getPrincipal();
    // TODO：构建指定用的 token, 先用测试用户
    SysUser user = new SysUser();
    user.setUsername("test");
    user.setId("123123L");
    UserToken userToken = authenticationService.buildToken(user);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String responseBody = new ObjectMapper().writeValueAsString(userToken);
    try (PrintWriter writer = response.getWriter()) {
      writer.write(responseBody);
    }
  }
}
