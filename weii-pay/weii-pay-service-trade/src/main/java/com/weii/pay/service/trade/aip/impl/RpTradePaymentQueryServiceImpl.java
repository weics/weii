package com.weii.pay.service.trade.aip.impl;

import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.trade.api.RpTradePaymentQueryService;
import com.weii.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.weii.pay.service.trade.dao.RpTradePaymentRecordDao;
import com.weii.pay.service.trade.entity.RpTradePaymentOrder;
import com.weii.pay.service.trade.entity.RpTradePaymentRecord;
import com.weii.pay.service.trade.enums.TradeStatusEnum;
import com.weii.pay.service.trade.vo.OrderPayResultVo;
import com.weii.pay.service.trade.vo.PaymentOrderQueryVo;
import com.weii.pay.service.user.api.RpUserPayConfigService;
import com.weii.pay.service.user.entity.RpUserPayConfig;
import com.weii.pay.service.user.exceptions.UserBizException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:14 2018/5/12
 * @Description:
 */
public class RpTradePaymentQueryServiceImpl implements RpTradePaymentQueryService {
    @Autowired
    private RpTradePaymentRecordDao rpTradePaymentRecordDao;

    @Autowired
    private RpTradePaymentOrderDao rpTradePaymentOrderDao;

    @Autowired
    private RpUserPayConfigService rpUserPayConfigService;

    /**
     * 根据参数查询交易记录List
     *
     * @param paramMap
     * @return
     */
    public List<RpTradePaymentRecord> listPaymentRecord(Map<String, Object> paramMap) {
        return rpTradePaymentRecordDao.listByColumn(paramMap);
    }

    /**
     * 根据商户支付KEY 及商户订单号 查询支付结果
     *
     * @param payKey
     *            商户支付KEY
     * @param orderNo
     *            商户订单号
     * @return
     */
    @Override
    public OrderPayResultVo getPayResult(String payKey, String orderNo) {

        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);
        if (rpUserPayConfig == null) {
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
        }

        String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
        RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();// 返回结果
        if (rpTradePaymentOrder != null && TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {// 支付记录为空,或者支付状态非成功
            orderPayResultVo.setStatus(PublicEnum.YES.name());// 设置支付状态
            orderPayResultVo.setOrderPrice(rpTradePaymentOrder.getOrderAmount());// 设置支付订单
            orderPayResultVo.setProductName(rpTradePaymentOrder.getProductName());// 设置产品名称
            orderPayResultVo.setReturnUrl(rpTradePaymentOrder.getReturnUrl());
        }

        return orderPayResultVo;
    }

    /**
     * 根据银行订单号查询支付记录
     *
     * @param bankOrderNo
     * @return
     */
    public RpTradePaymentRecord getRecordByBankOrderNo(String bankOrderNo) {
        return rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
    }

    /**
     * 根据支付流水号查询支付记录
     *
     * @param trxNo
     * @return
     */
    public RpTradePaymentRecord getRecordByTrxNo(String trxNo){
        return rpTradePaymentRecordDao.getByTrxNo(trxNo);
    }

    /**
     * 分页查询支付订单
     *
     * @param pageParam
     * @param paymentOrderQueryVo
     * @return
     */
    @Override
    public PageBean<RpTradePaymentOrder> listPaymentOrderPage(PageParam pageParam, PaymentOrderQueryVo paymentOrderQueryVo) {

        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",paymentOrderQueryVo.getMerchantNo());//商户编号
        paramMap.put("merchantName",paymentOrderQueryVo.getMerchantName());//商户名称
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getMerchantOrderNo());//商户订单号
        paramMap.put("fundIntoType",paymentOrderQueryVo.getFundIntoType());//资金流入类型
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getOrderDateBegin());//订单开始时间
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getOrderDateEnd());//订单结束时间
        paramMap.put("payTypeName",paymentOrderQueryVo.getPayTypeName());//支付类型
        paramMap.put("payWayName",paymentOrderQueryVo.getPayWayName());//支付类型
        paramMap.put("status",paymentOrderQueryVo.getStatus());//支付状态

        if (paymentOrderQueryVo.getOrderDateBegin() != null){
            paramMap.put("orderDateBegin", paymentOrderQueryVo.getOrderDateBegin());//支付状态
        }

        if (paymentOrderQueryVo.getOrderDateEnd() != null){
            paramMap.put("orderDateEnd", paymentOrderQueryVo.getOrderDateEnd());//支付状态
        }

        return rpTradePaymentOrderDao.listPage(pageParam,paramMap);
    }

    /**
     * 分页查询支付记录
     *
     * @param pageParam
     * @param paymentOrderQueryVo
     * @return
     */
    @Override
    public PageBean<RpTradePaymentRecord> listPaymentRecordPage(PageParam pageParam, PaymentOrderQueryVo paymentOrderQueryVo) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",paymentOrderQueryVo.getMerchantNo());//商户编号
        paramMap.put("merchantName",paymentOrderQueryVo.getMerchantName());//商户名称
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getMerchantOrderNo());//商户订单号
        paramMap.put("fundIntoType",paymentOrderQueryVo.getFundIntoType());//资金流入类型
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getOrderDateBegin());//订单开始时间
        paramMap.put("merchantOrderNo",paymentOrderQueryVo.getOrderDateEnd());//订单结束时间
        paramMap.put("payTypeName",paymentOrderQueryVo.getPayTypeName());//支付类型
        paramMap.put("payWayName",paymentOrderQueryVo.getPayWayName());//支付类型
        paramMap.put("status",paymentOrderQueryVo.getStatus());//支付状态

        if (paymentOrderQueryVo.getOrderDateBegin() != null){
            paramMap.put("orderDateBegin", paymentOrderQueryVo.getOrderDateBegin());//支付状态
        }

        if (paymentOrderQueryVo.getOrderDateEnd() != null){
            paramMap.put("orderDateEnd", paymentOrderQueryVo.getOrderDateEnd());//支付状态
        }

        return rpTradePaymentRecordDao.listPage(pageParam,paramMap);
    }
}