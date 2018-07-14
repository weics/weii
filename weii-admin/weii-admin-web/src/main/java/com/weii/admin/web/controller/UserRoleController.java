package com.weii.admin.web.controller;

import com.weii.admin.service.api.UserRoleService;
import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.User;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: weics
 * @Date: Created in 21:10 2018/06/24
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("userrole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;


    @PutMapping
    public WeiiResult updateUserRole(@RequestBody final User user) {
        this.userRoleService.updateUserRole(user);
        return WeiiResult.ok();
    }
}
