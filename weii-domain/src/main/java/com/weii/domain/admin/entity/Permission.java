package com.weii.domain.admin.entity;

import java.io.Serializable;
import java.util.Date;

public class Permission implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_id
     *
     * @mbggenerated
     */
    private Integer permissionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_name
     *
     * @mbggenerated
     */
    private String permissionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_code
     *
     * @mbggenerated
     */
    private String permissionCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.menu_code
     *
     * @mbggenerated
     */
    private String menuCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.menu_name
     *
     * @mbggenerated
     */
    private String menuName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.menu_type
     *
     * @mbggenerated
     */
    private Integer menuType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.created_time
     *
     * @mbggenerated
     */
    private Date createdTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table permission
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_id
     *
     * @return the value of permission.permission_id
     *
     * @mbggenerated
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_id
     *
     * @param permissionId the value for permission.permission_id
     *
     * @mbggenerated
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_name
     *
     * @return the value of permission.permission_name
     *
     * @mbggenerated
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_name
     *
     * @param permissionName the value for permission.permission_name
     *
     * @mbggenerated
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_code
     *
     * @return the value of permission.permission_code
     *
     * @mbggenerated
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_code
     *
     * @param permissionCode the value for permission.permission_code
     *
     * @mbggenerated
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.menu_code
     *
     * @return the value of permission.menu_code
     *
     * @mbggenerated
     */
    public String getMenuCode() {
        return menuCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.menu_code
     *
     * @param menuCode the value for permission.menu_code
     *
     * @mbggenerated
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.menu_name
     *
     * @return the value of permission.menu_name
     *
     * @mbggenerated
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.menu_name
     *
     * @param menuName the value for permission.menu_name
     *
     * @mbggenerated
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.menu_type
     *
     * @return the value of permission.menu_type
     *
     * @mbggenerated
     */
    public Integer getMenuType() {
        return menuType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.menu_type
     *
     * @param menuType the value for permission.menu_type
     *
     * @mbggenerated
     */
    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.created_time
     *
     * @return the value of permission.created_time
     *
     * @mbggenerated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.created_time
     *
     * @param createdTime the value for permission.created_time
     *
     * @mbggenerated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.update_time
     *
     * @return the value of permission.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.update_time
     *
     * @param updateTime the value for permission.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table permission
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permissionId=").append(permissionId);
        sb.append(", permissionName=").append(permissionName);
        sb.append(", permissionCode=").append(permissionCode);
        sb.append(", menuCode=").append(menuCode);
        sb.append(", menuName=").append(menuName);
        sb.append(", menuType=").append(menuType);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}