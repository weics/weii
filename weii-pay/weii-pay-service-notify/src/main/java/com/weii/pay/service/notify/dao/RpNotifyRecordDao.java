package com.weii.pay.service.notify.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.notify.entity.NotifyRecord;

public interface RpNotifyRecordDao  extends BaseDao<NotifyRecord> {

    NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType);

    int deleteByPrimaryKey(String id);

    int insertSelective(NotifyRecord record);

    NotifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NotifyRecord record);

    int updateByPrimaryKey(NotifyRecord record);
}