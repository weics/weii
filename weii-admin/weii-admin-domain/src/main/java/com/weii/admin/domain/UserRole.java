package com.weii.admin.domain;

/**
 * @Author: weics
 * @Date: Created in 21:39 2018/06/24
 * @Description:
 * @Modified By:
 */
public class UserRole {
    /**
     * 用户Id
     */

    private Long userId;


    private Long roleId;

    public UserRole setRoleId(final Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public UserRole setUserId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getRoleId() {
        return this.roleId;
    }
}
