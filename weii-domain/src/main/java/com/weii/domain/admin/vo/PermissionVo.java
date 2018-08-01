package com.weii.domain.admin.vo;

/**
 * @Author: weics
 * @Date: Created in 22:06 2018/8/1
 * @Description:
 * @Modified By:
 */
public class PermissionVo {

    private Integer userId;
    private String roleId;
    private String menuCode;
    private String permissionCode;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
