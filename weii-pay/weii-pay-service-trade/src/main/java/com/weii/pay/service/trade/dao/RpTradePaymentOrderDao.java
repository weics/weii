
package com.weii.pay.service.trade.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.trade.entity.TradePaymentOrder;

public interface RpTradePaymentOrderDao  extends BaseDao<TradePaymentOrder> {

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    TradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

    int deleteByPrimaryKey(String id);

    int insertSelective(TradePaymentOrder record);

    TradePaymentOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TradePaymentOrder record);

    int updateByPrimaryKey(TradePaymentOrder record);
    
   

}
