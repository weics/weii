package com.weii.pay.service.trade.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.trade.entity.TradePaymentRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 12:46 2018/8/26
 * @Description:
 * @Modified By:
 */
@Repository
public interface TradePaymentRecordMapper {
    TradePaymentRecord getByBankOrderNo(String bankOrderNo);

    void update(TradePaymentRecord tradePaymentRecord);

    void insert(TradePaymentRecord tradePaymentRecord);

    String buildTrxNo();

    String buildBankOrderNo();

    List<TradePaymentRecord> listByColumn(Map<String,Object> paramMap);

    TradePaymentRecord getByTrxNo(String trxNo);

    PageBean<TradePaymentRecord> listPage(PageParam pageParam, Map<String,Object> paramMap);
}
