package com.weii.pay.service.notify.dao.impl;


import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.service.notify.dao.RpNotifyRecordLogDao;
import com.weii.pay.service.notify.entity.NotifyRecordLog;
import org.springframework.stereotype.Repository;

@Repository("rpNotifyRecordLogDao")
public class RpNotifyRecordLogDaoImpl extends BaseDaoImpl<NotifyRecordLog> implements RpNotifyRecordLogDao {
    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insertSelective(NotifyRecordLog record) {
        return 0;
    }

    @Override
    public NotifyRecordLog selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(NotifyRecordLog record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(NotifyRecordLog record) {
        return 0;
    }
}
