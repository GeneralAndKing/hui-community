package cn.hui_community.service.configuration.security.authentication.password;

import cn.hui_community.service.configuration.security.authentication.GlobalAuthenticationFilter;
import cn.hui_community.service.configuration.security.authentication.GlobalAuthenticationToken;
import cn.hui_community.service.configuration.security.authentication.GlobalWebAuthenticationDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class PasswordAuthenticationFilter extends  GlobalAuthenticationFilter {

  private final ObjectMapper objectMapper;

  public PasswordAuthenticationFilter(ObjectMapper objectMapper) {
    super("/login", HttpMethod.POST);
    this.objectMapper = objectMapper;
  }

  @Override
  public GlobalAuthenticationToken combinationAuthentication(HttpServletRequest request) throws IOException {
    String requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
    PasswordAuthenticationRequest authentication = objectMapper.readValue(requestBody, PasswordAuthenticationRequest.class);
    GlobalWebAuthenticationDetails details = (GlobalWebAuthenticationDetails) authenticationDetailsSource.buildDetails(
        request);
//    details
//        .setClientType(ClientType.WeChatMiniProgram)
//        .setLoginType(LoginType.PhoneSms);
    return  new PasswordAuthenticationToken(authentication.username(), authentication.password(), details);
  }
}
