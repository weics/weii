package com.weii.pay.service.trade.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.trade.entity.TradePaymentOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 12:45 2018/8/26
 * @Description:
 * @Modified By:
 */
@Repository
public interface TradePaymentOrderMapper {
    TradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo, String orderNo);

    void insert(TradePaymentOrder tradePaymentOrder);

    void update(TradePaymentOrder tradePaymentOrder);

    List<TradePaymentOrder> listPage(Map<String,Object> paramMap);
}
