package com.weii.pay.service.trade.api;

import com.weii.pay.service.trade.exceptions.TradeBizException;
import com.weii.pay.service.trade.vo.ScanPayResultVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: weics
 * @Date: Created in 0:17 2018/5/7
 * @Description:
 * @Modified By:
 */
public interface RpTradePaymentManagerService {

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     * @param payKey    商户支付Key
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额(元)
     * @param payWayCode    支付方式
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期(分钟)
     * @param returnUrl 支付结果页面通知地址
     * @param notifyUrl 支付结果后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     */
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl
            , String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) throws TradeBizException;
}
