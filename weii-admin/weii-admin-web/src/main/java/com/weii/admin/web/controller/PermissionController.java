package com.weii.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weii.admin.service.api.PermissionService;
import com.weii.common.pojo.WeiiResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 20:51 2018/06/24
 * @Description:
 * @Modified By:
 */
public class PermissionController {

    @Resource
    private PermissionService permissionService;


    @GetMapping
    public WeiiResult listResourcePermission(@RequestParam(defaultValue = "0") final Integer page,
                                             @RequestParam(defaultValue = "0") final Integer size) {
        PageHelper.startPage(page, size);
        final List<JSONObject> list = this.permissionService.findAllResourcePermission();
        //noinspection unchecked
        final PageInfo pageInfo = new PageInfo(list);
        return WeiiResult.ok(pageInfo);
    }
}
