package cn.hui_community.service.configuration.security.authentication.wechat;

import cn.hui_community.service.component.WechatMiniClient;
import cn.hui_community.service.model.User;
import cn.hui_community.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;


@RequiredArgsConstructor
@Component
public class UserWechatMiniAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final WechatMiniClient wechatMiniClient;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserWechatMiniTokenAuthenticationToken authenticationToken = (UserWechatMiniTokenAuthenticationToken) authentication;
        String username = authenticationToken.getPrincipal().toString();
        String code = authenticationToken.getCredentials().toString();
        String openId = wechatMiniClient.authUserOpenId(code);
        User user = userRepository.findByOpenId(openId)
                .orElseGet(() -> userRepository.save(User.builder()
                        .openId(openId)
                        .name(username)
                        .build()
                ));
        return UserWechatMiniTokenAuthenticationToken.authenticated(user, openId, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserWechatMiniTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
