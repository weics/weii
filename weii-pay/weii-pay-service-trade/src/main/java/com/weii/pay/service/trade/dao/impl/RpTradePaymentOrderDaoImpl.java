package com.weii.pay.service.trade.dao.impl;


import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.weii.pay.service.trade.entity.RpTradePaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository("rpTradePaymentOrderDao")
public class RpTradePaymentOrderDaoImpl extends BaseDaoImpl<RpTradePaymentOrder> implements RpTradePaymentOrderDao {
	

    /**
     * 根据商户编号及商户订单号获取支付订单信息
     * @param merchantNo    商户编号
     * @param merchantOrderNo   商户订单号
     * @return
     */
    @Override
    public RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(RpTradePaymentOrder record) {
        return 0;
    }

    @Override
    public RpTradePaymentOrder selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(RpTradePaymentOrder record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(RpTradePaymentOrder record) {
        return 0;
    }
}
