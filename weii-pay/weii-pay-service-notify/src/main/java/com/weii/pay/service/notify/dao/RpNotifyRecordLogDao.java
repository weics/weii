package com.weii.pay.service.notify.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.notify.entity.RpNotifyRecordLog;

public interface RpNotifyRecordLogDao  extends BaseDao<RpNotifyRecordLog> {


    int deleteByPrimaryKey(String id);

    int insertSelective(RpNotifyRecordLog record);

    RpNotifyRecordLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RpNotifyRecordLog record);

    int updateByPrimaryKey(RpNotifyRecordLog record);
}