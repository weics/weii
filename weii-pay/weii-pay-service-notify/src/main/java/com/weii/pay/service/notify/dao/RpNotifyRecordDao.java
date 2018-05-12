package com.weii.pay.service.notify.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.notify.entity.RpNotifyRecord;

public interface RpNotifyRecordDao  extends BaseDao<RpNotifyRecord> {

    RpNotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType);

    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecord record);

    RpNotifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RpNotifyRecord record);

    int updateByPrimaryKey(RpNotifyRecord record);
}