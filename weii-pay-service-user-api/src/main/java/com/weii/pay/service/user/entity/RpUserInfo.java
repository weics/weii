package com.weii.pay.service.user.entity;

import com.weii.pay.common.core.entity.BaseEntity;
import com.weii.pay.common.core.enums.PublicStatusEnum;

import java.io.Serializable;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:43 2018/5/11
 * @Description: 支付产品实体类
 */
public class RpUserInfo extends BaseEntity implements Serializable {
    private String userNo;

    private String userName;

    private String accountNo;

    private static final long serialVersionUID = 1L;


    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getStatusDesc() {
        return PublicStatusEnum.getEnum(this.getStatus()).getDesc();
    }

}
