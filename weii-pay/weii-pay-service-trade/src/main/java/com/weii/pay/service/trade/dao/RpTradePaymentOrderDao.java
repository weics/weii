
package com.weii.pay.service.trade.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.trade.entity.RpTradePaymentOrder;

public interface RpTradePaymentOrderDao  extends BaseDao<RpTradePaymentOrder> {

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

    int deleteByPrimaryKey(String id);

    int insertSelective(RpTradePaymentOrder record);

    RpTradePaymentOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RpTradePaymentOrder record);

    int updateByPrimaryKey(RpTradePaymentOrder record);
    
   

}
