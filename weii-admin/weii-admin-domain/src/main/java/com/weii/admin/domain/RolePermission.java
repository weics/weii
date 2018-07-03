package com.weii.admin.domain;

/**
 * @Author: weics
 * @Date: Created in 21:21 2018/06/24
 * @Description:
 * @Modified By:
 */
public class RolePermission {


    private Long roleId;

    private Long permissionId;

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(final Long permissionId) {
        this.permissionId = permissionId;
    }
}
