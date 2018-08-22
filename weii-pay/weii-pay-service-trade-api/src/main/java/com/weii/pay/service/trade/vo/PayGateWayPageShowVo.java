package com.weii.pay.service.trade.vo;

import com.weii.pay.common.core.enums.PayWayEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:19 2018/5/9
 * @Description: 支付网关页面展示实体
 * @Modified By:
 */
public class PayGateWayPageShowVo implements Serializable {
    private static final long serialVersionUID = -8326765365969156567L;

    /** 订单金额 **/
    private BigDecimal orderAmount;
    /** 产品名称 **/
    private String productName;

    private String merchantName;
    /** 商户订单号 **/
    private String merchantOrderNo;
    /** 商户支付key **/
    private String payKey;
    /** 支付方式列表 **/
    private Map<String,PayWayEnum> payWayEnumMap;

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public Map<String, PayWayEnum> getPayWayEnumMap() {
        return payWayEnumMap;
    }

    public void setPayWayEnumMap(Map<String, PayWayEnum> payWayEnumMap) {
        this.payWayEnumMap = payWayEnumMap;
    }
}
