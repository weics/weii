package com.weii.pay.service.notify.dao.impl;


import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.service.notify.dao.RpNotifyRecordDao;
import com.weii.pay.service.notify.entity.NotifyRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("rpNotifyRecordDao")
public class RpNotifyRecordDaoImpl extends BaseDaoImpl<NotifyRecord> implements RpNotifyRecordDao {


    @Override
    public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("merchantOrderNo",merchantOrderNo);
        paramMap.put("notifyType",notifyType);

        return super.getBy(paramMap);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(NotifyRecord record) {
        return 0;
    }

    @Override
    public NotifyRecord selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(NotifyRecord record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(NotifyRecord record) {
        return 0;
    }

}
