package cn.hui_community.service.service.impl;

import cn.hui_community.service.model.SysUser;
import cn.hui_community.service.model.dto.SysUserResponse;
import cn.hui_community.service.repository.SysUserRepository;
import cn.hui_community.service.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;

    @Override
    public SysUserResponse addSysUser(SysUser sysUser) {
        return null;
    }
}
