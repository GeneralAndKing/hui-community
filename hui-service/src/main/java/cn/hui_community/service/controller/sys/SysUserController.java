package cn.hui_community.service.controller.sys;

import cn.hui_community.service.model.dto.SysUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys-user")
public class SysUserController {
    @PostMapping("")
    public SysUserResponse addSysUser() {
        return null;
    }
}
