package com.weii.pay.service.notify.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.notify.entity.NotifyRecordLog;

public interface RpNotifyRecordLogDao  extends BaseDao<NotifyRecordLog> {


    int deleteByPrimaryKey(String id);

    int insertSelective(NotifyRecordLog record);

    NotifyRecordLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NotifyRecordLog record);

    int updateByPrimaryKey(NotifyRecordLog record);
}