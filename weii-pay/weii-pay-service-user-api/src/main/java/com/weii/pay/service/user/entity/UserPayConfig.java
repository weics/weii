package com.weii.pay.service.user.entity;

import com.weii.pay.common.core.entity.BaseEntity;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.service.user.enums.FundInfoTypeEnum;

import java.io.Serializable;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:44 2018/5/11
 * @Description: 用户支付配置实体类
 */
public class UserPayConfig extends BaseEntity implements Serializable {
    private String auditStatus;

    private String isAutoSett;

    private String productCode;

    private String productName;

    private String userNo;

    private String userName;

    private Integer riskDay;

    private String payKey;

    private String fundIntoType;

    private String paySecret;

    public String getPaySecret() {
        return paySecret;
    }

    public void setPaySecret(String paySecret) {
        this.paySecret = paySecret;
    }

    public String getFundIntoType() {
        return fundIntoType;
    }

    public void setFundIntoType(String fundIntoType) {
        this.fundIntoType = fundIntoType;
    }

    private static final long serialVersionUID = 1L;

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getIsAutoSett() {
        return isAutoSett;
    }

    public void setIsAutoSett(String isAutoSett) {
        this.isAutoSett = isAutoSett == null ? null : isAutoSett.trim();
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

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

    public Integer getRiskDay() {
        return riskDay;
    }

    public void setRiskDay(Integer riskDay) {
        this.riskDay = riskDay;
    }

    public String getAuditStatusDesc() {
        return PublicEnum.getEnum(this.getAuditStatus()).getDesc();
    }

    public String getFundIntoTypeDesc() {
        return FundInfoTypeEnum.getEnum(this.getFundIntoType()).getDesc();
    }
}
