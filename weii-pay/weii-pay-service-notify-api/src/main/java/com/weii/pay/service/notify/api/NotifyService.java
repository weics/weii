package com.weii.pay.service.notify.api;


import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.entity.NotifyRecordLog;
import com.weii.pay.service.notify.exceptions.NotifyBizException;

import java.util.Map;

public interface NotifyService {

    /**
     * 发送消息通知
     * @param notifyUrl 通知地址
     * @param merchantOrderNo   商户订单号
     * @param merchantNo    商户编号
     */
    public void notifySend(String notifyUrl, String merchantOrderNo, String merchantNo) throws NotifyBizException;


    /**
     * 通过ID获取通知记录
     * @param id
     * @return
     */
    public NotifyRecord getNotifyRecordById(String id) throws NotifyBizException;

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     * @param merchantNo    商户编号
     * @param merchantOrderNo   商户订单号
     * @param notifyType    消息类型
     * @return
     */
    public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) throws NotifyBizException;


    public PageBean<NotifyRecord> queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap) throws NotifyBizException;
    /**
     * 创建消息通知
     * @param notifyRecord
     */
    public long createNotifyRecord(NotifyRecord notifyRecord) throws NotifyBizException;

    /**
     * 修改消息通知
     * @param notifyRecord
     */
    public void updateNotifyRecord(NotifyRecord notifyRecord) throws NotifyBizException;

    /**
     * 创建消息通知记录
     * @param notifyRecordLog
     * @return
     */
    public long createNotifyRecordLog(NotifyRecordLog notifyRecordLog) throws NotifyBizException;

}
