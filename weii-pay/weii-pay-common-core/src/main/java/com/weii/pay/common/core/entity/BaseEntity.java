package com.weii.pay.common.core.entity;

import com.weii.pay.common.core.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    private String id = StringUtil.get32UUID();
    /**
     * 版本号默认为0
     */
    private Integer version = 0;
    /**
     * 状态 PublicStatusEnum
     */
    private String status;
    /**
     * 创建人.
     */
    private String creater;
    /**
     * 创建时间.
     */
    private Date createTime = new Date();
    /**
     * 修改人.
     */
    private String editor;
    /**
     * 修改时间.
     */
    private Date editTime;
    /**
     * 描述
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
