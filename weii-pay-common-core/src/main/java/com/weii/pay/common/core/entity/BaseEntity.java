package com.weii.pay.common.core.entity;

import com.weii.pay.common.core.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by weics on 2018/5/1.
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id = StringUtil.get32UUID();// 主键ID.
    private Integer version = 0;// 版本号默认为0
    private String status;// 状态 PublicStatusEnum
    private String creater;// 创建人.
    private Date createTime = new Date();// 创建时间.
    private String editor;// 修改人.
    private Date editTime;// 修改时间.
    private String remark;// 描述
}
