package com.weii.admin.domain;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:38 2018/06/24
 * @Description:
 * @Modified By:
 */
public class User {

    /**
     * 用户Id
     */

    private Long id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String resume;

    /**
     * 注册时间
     */

    private Timestamp registerTime;

    /**
     * 上一次登录时间
     */

    private Timestamp loginTime;

    /* ---------- 以下字段来自联表查询 ------------*/
    /**
     * 用户的角色Id
     */
    private Long roleId;

    /**
     * 用户的角色名
     */
    private String roleName;

    /**
     * 用户的角色对应的权限code
     */
    private List<String> permissionCodeList;


    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(final String resume) {
        this.resume = resume;
    }

    public Timestamp getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(final Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public List<String> getPermissionCodeList() {
        return this.permissionCodeList;
    }

    public void setPermissionCodeList(final List<String> permissionCodeList) {
        this.permissionCodeList = permissionCodeList;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
