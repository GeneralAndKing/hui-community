package cn.hui_community.service.service;

import cn.hui_community.service.model.Token;
import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.User;

public interface TokenService {


    /**
     * Build user token info when user authentication success.
     *
     * @param user user authentication success
     * @return token
     */
    Token buildTokenFromSysUser(SysUser user);

    Token buildTokenFromUser(User user);

    Boolean validateRefreshToken(String id,String refreshToken);
}
