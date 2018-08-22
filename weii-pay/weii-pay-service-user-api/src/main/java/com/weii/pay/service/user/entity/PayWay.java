package com.weii.pay.service.user.entity;

import com.weii.pay.common.core.entity.BaseEntity;

import java.io.Serializable;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:42 2018/5/11
 * @Description: 支付方式实体类
 */
public class PayWay extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -992367769827189992L;

    private String payWayCode;

    private String payWayName;

    private String payTypeCode;

    private String payTypeName;

    private String payProductCode;

    private Integer sorts;

    private Double payRate;


    public String getPayWayCode() {
        return payWayCode;
    }

    public void setPayWayCode(String payWayCode) {
        this.payWayCode = payWayCode == null ? null : payWayCode.trim();
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName == null ? null : payWayName.trim();
    }

    public String getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(String payTypeCode) {
        this.payTypeCode = payTypeCode == null ? null : payTypeCode.trim();
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName == null ? null : payTypeName.trim();
    }

    public String getPayProductCode() {
        return payProductCode;
    }

    public void setPayProductCode(String payProductCode) {
        this.payProductCode = payProductCode == null ? null : payProductCode.trim();
    }


    public Integer getSorts() {
        return sorts;
    }

    public void setSorts(Integer sorts) {
        this.sorts = sorts;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }
}
