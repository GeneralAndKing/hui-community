package cn.hui_community.service.service;

import cn.hui_community.service.configuration.security.Token;
import cn.hui_community.service.model.SysUser;

public interface AuthenticationService {


    /**
     * Build user token info when user authentication success.
     *
     * @param user user authentication success
     * @return user token
     * @see GlobalAuthenticationSuccessHandler
     */
    Token buildTokenFromSysUser(SysUser user);
}
