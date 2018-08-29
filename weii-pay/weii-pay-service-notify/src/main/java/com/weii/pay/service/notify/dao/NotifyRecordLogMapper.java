package com.weii.pay.service.notify.dao;

import com.weii.pay.service.notify.entity.NotifyRecordLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: weics
 * @Date: Created in 23:27 2018/8/29
 * @Description:
 * @Modified By:
 */
@Mapper
public interface NotifyRecordLogMapper {
    long insert(NotifyRecordLog notifyRecordLog);
}
