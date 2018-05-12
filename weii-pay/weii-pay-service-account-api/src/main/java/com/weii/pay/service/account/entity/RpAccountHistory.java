package com.weii.pay.service.account.entity;

import com.weii.pay.common.core.entity.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:57 2018/5/12
 * @Description: 账户历史信息
 */
public class RpAccountHistory extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 8042813534905891885L;
    /** 账户编号 **/
    private String accountNo;

    /** 金额 **/
    private BigDecimal amount;

    /** 账户余额 **/
    private BigDecimal balance;

    /** 资金变动方向 **/
    private String fundDirection;

    /** 是否允许结算 **/
    private String isAllowSett;

    /** 是否完成结算 **/
    private String isCompleteSett;

    /** 请求号 **/
    private String requestNo;

    /** 银行流水号 **/
    private String bankTrxNo;

    /** 业务类型 **/
    private String trxType;

    /** 风险预存期 **/
    private Integer riskDay;

    /** 用户编号 **/
    private String userNo;

    /** 用户名 **/
    private String userName;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getFundDirection() {
        return fundDirection;
    }

    public void setFundDirection(String fundDirection) {
        this.fundDirection = fundDirection;
    }

    public String getIsAllowSett() {
        return isAllowSett;
    }

    public void setIsAllowSett(String isAllowSett) {
        this.isAllowSett = isAllowSett;
    }

    public String getIsCompleteSett() {
        return isCompleteSett;
    }

    public void setIsCompleteSett(String isCompleteSett) {
        this.isCompleteSett = isCompleteSett;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getBankTrxNo() {
        return bankTrxNo;
    }

    public void setBankTrxNo(String bankTrxNo) {
        this.bankTrxNo = bankTrxNo;
    }

    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public Integer getRiskDay() {
        return riskDay;
    }

    public void setRiskDay(Integer riskDay) {
        this.riskDay = riskDay;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
