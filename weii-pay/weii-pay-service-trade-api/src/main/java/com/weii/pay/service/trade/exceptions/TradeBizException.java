package com.weii.pay.service.trade.exceptions;

import com.weii.pay.common.core.exception.BizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: weics
 * @Date: Created in 0:19 2018/5/7
 * @Description: 支付业务异常类
 * @Modified By:
 */
public class TradeBizException extends BizException {
    private static final long serialVersionUID = 7190361204026675557L;

    /** 支付订单号重复 **/
    public static final int TRADE_ORDER_NO_REPEAT_ERROR = 101;

    /** 错误的支付方式 **/
    public static final int TRADE_PAY_WAY_ERROR = 102;

    /** 微信异常 **/
    public static final int TRADE_WEIXIN_ERROR = 103;
    /** 订单异常 **/
    public static final int TRADE_ORDER_ERROR = 104;

    /** 交易记录状态不为成功 **/
    public static final int TRADE_ORDER_STATUS_NOT_SUCCESS = 105;

    /** 支付宝异常 **/
    public static final int TRADE_ALIPAY_ERROR = 106;

    /** 参数异常 **/
    public static final int TRADE_PARAM_ERROR = 107;

    /** 交易系统异常 **/
    public static final int TRADE_SYSTEM_ERROR = 108;

    private static final Log LOG = LogFactory.getLog(TradeBizException.class);


    public TradeBizException() {
    }

    public TradeBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public TradeBizException(int code, String msg) {
        super(code, msg);
    }

    public TradeBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
