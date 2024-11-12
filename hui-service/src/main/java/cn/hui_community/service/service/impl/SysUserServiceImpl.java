package cn.hui_community.service.service.impl;

import cn.hui_community.service.repository.SysUserRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;

@Server
@RequiredArgsConstructor
public class SysUserServiceImpl {
    private final SysUserRepository sysUserRepository;

}
