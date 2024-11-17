package cn.hui_community.service.service;

import cn.hui_community.service.model.Token;
import cn.hui_community.service.model.SysUser;

public interface TokenService {


    /**
     * Build user token info when user authentication success.
     *
     * @param user user authentication success
     * @return token
     */
    Token buildTokenFromSysUser(SysUser user);

    Boolean validateRefreshToken(String id,String refreshToken);
}
