package com.weii.admin.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:19 2018/06/24
 * @Description:
 * @Modified By:
 */
public class Role {
    /**
     * 角色Id
     */
    private Long id;


    private String name;

    /* ---------- 以下字段来自联表查询 ------------*/

    /**
     * 角色对应的权限
     */

    private List<Resource> resourceList;

    /* ---------- 以下字段来自请求的Json ------------*/

    /**
     * 角色对应的权限Id
     */
    @JSONField(serialize = false)
    private List<Integer> permissionIdList;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Resource> getResourceList() {
        return this.resourceList;
    }

    public void setResourceList(final List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public List<Integer> getPermissionIdList() {
        return this.permissionIdList;
    }

    public void setPermissionIdList(final List<Integer> permissionIdList) {
        this.permissionIdList = permissionIdList;
    }
}
