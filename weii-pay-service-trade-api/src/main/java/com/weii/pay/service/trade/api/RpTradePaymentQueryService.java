package com.weii.pay.service.trade.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.trade.entity.RpTradePaymentOrder;
import com.weii.pay.service.trade.entity.RpTradePaymentRecord;
import com.weii.pay.service.trade.vo.OrderPayResultVo;
import com.weii.pay.service.trade.vo.PaymentOrderQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:24 2018/5/9
 * @Description: 交易模块查询接口类.
 * @Modified By:
 */
public interface RpTradePaymentQueryService {
    /**
     * 根据参数查询交易记录List
     *
     * @param paremMap
     * @return
     */
    public List<RpTradePaymentRecord> listPaymentRecord(Map<String, Object> paremMap);

    /**
     * 根据商户支付KEY 及商户订单号 查询支付结果
     *
     * @param payKey
     *            商户支付KEY
     * @param orderNo
     *            商户订单号
     * @return
     */
    public OrderPayResultVo getPayResult(String payKey, String orderNo);

    /**
     * 根据银行订单号查询支付记录
     *
     * @param bankOrderNo
     * @return
     */
    public RpTradePaymentRecord getRecordByBankOrderNo(String bankOrderNo);

    /**
     * 根据支付流水号查询支付记录
     *
     * @param trxNo
     * @return
     */
    public RpTradePaymentRecord getRecordByTrxNo(String trxNo);


    /**
     * 分页查询支付订单
     * @param pageParam
     * @param paymentOrderQueryVo
     * @return
     */
    public PageBean<RpTradePaymentOrder> listPaymentOrderPage(PageParam pageParam , PaymentOrderQueryVo paymentOrderQueryVo);

    /**
     * 分页查询支付记录
     * @param pageParam
     * @param paymentOrderQueryVo
     * @return
     */
    public PageBean<RpTradePaymentRecord> listPaymentRecordPage(PageParam pageParam ,PaymentOrderQueryVo paymentOrderQueryVo);

}
