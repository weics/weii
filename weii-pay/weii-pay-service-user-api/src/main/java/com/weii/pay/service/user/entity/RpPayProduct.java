package com.weii.pay.service.user.entity;

import com.weii.pay.common.core.entity.BaseEntity;
import com.weii.pay.common.core.enums.PublicEnum;

import java.io.Serializable;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:41 2018/5/11
 * @Description: 支付产品实体类
 */
public class RpPayProduct  extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 514313391810904644L;

    private String productCode;

    private String productName;

    private String auditStatus;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getAuditStatusDesc() {
        return PublicEnum.getEnum(this.getAuditStatus()).getDesc();
    }
}
