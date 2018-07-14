package com.weii.admin.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weii.admin.service.api.RoleService;

import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:09 2018/06/24
 * @Description: 超级管理员添加角色控制
 * @Modified By:
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequiresPermissions("role:add")
    @PostMapping
    public WeiiResult add(@RequestBody final Role role) {
//        this.roleService.save(role);
        return WeiiResult.ok();
    }

    @RequiresPermissions("role:del")
    @DeleteMapping("/{id}")
    public WeiiResult delete(@PathVariable final Long id) {
        this.roleService.deleteById(id);
        return WeiiResult.ok();
    }

    @RequiresPermissions("role:update")
    @PutMapping
    public WeiiResult update(@RequestBody final Role role) {
        this.roleService.update(role);
        return WeiiResult.ok();
    }

    @RequiresPermissions("role:list")
    @RequiresRoles("role")
    @GetMapping
    public WeiiResult list(@RequestParam(defaultValue = "0") final Integer page,
                       @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<String> list = this.roleService.findAllRoleWithPermission();
        //noinspection unchecked
        final PageInfo pageInfo = new PageInfo(list);
        return WeiiResult.ok(pageInfo);
    }

}
