package com.weii.pay.service.trade.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.trade.entity.TradePaymentRecord;

public interface RpTradePaymentRecordDao extends BaseDao<TradePaymentRecord> {
	
	/** 获取支付流水号 **/
	String buildTrxNo();
	
	/** 获取银行订单号 **/
	String buildBankOrderNo();
	
	
    int deleteByPrimaryKey(String id);

    int insertSelective(TradePaymentRecord record);

    TradePaymentRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TradePaymentRecord record);

    /**
     * 根据银行订单号获取支付信息
     * @param bankOrderNo
     * @return
     */
    TradePaymentRecord getByBankOrderNo(String bankOrderNo);

    /**
     * 根据商户编号及商户订单号获取支付结果
     * @param merchantNo
     * @param merchantOrderNo
     * @return
     */
    TradePaymentRecord getByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

    /**
	 * 根据支付流水号查询支付记录
	 * 
	 * @param trxNo
	 * @return
	 */
	TradePaymentRecord getByTrxNo(String trxNo);

}
