package cn.hui_community.service.service;

import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.dto.SysUserResponse;

public interface SysUserService {

    SysUserResponse addSysUser(SysUser sysUser);
}
