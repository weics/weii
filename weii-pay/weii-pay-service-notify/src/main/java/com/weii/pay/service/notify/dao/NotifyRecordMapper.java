package com.weii.pay.service.notify.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.notify.entity.NotifyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:26 2018/8/29
 * @Description:
 * @Modified By:
 */
@Mapper
public interface NotifyRecordMapper {
    NotifyRecord getById(String id);

    NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType);

    PageBean<NotifyRecord> listPage(PageParam pageParam, Map<String,Object> paramMap);

    long insert(NotifyRecord notifyRecord);

    void update(NotifyRecord notifyRecord);
}
