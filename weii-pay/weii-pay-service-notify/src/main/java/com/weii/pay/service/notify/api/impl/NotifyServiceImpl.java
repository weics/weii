package com.weii.pay.service.notify.api.impl;


import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.notify.api.NotifyService;
import com.weii.pay.service.notify.dao.RpNotifyRecordDao;
import com.weii.pay.service.notify.dao.RpNotifyRecordLogDao;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.entity.NotifyRecordLog;
import com.weii.pay.service.notify.enums.NotifyStatusEnum;
import com.weii.pay.service.notify.enums.NotifyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service("rpNotifyService")
public class NotifyServiceImpl implements NotifyService {



    @Autowired
    private RpNotifyRecordDao rpNotifyRecordDao;

    @Autowired
    private RpNotifyRecordLogDao rpNotifyRecordLogDao;
    /**
     * 发送消息通知
     *
     * @param notifyUrl       通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo      商户编号
     */
    @Override
    public void notifySend(String notifyUrl, String merchantOrderNo, String merchantNo) {

        NotifyRecord record = new NotifyRecord();
        record.setNotifyTimes(0);
        record.setLimitNotifyTimes(5);
        record.setStatus(NotifyStatusEnum.CREATED.name());
        record.setUrl(notifyUrl);
        record.setMerchantOrderNo(merchantOrderNo);
        record.setMerchantNo(merchantNo);
        record.setNotifyType(NotifyTypeEnum.MERCHANT.name());

//        Object toJSON = JSONObject.toJSON(record);
//        final String str = toJSON.toString();
//
//        notifyJmsTemplate.send(new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                return session.createTextMessage(str);
//            }
//        });
    }

    /**
     * 通过ID获取通知记录
     *
     * @param id
     * @return
     */
    @Override
    public NotifyRecord getNotifyRecordById(String id) {
        return rpNotifyRecordDao.getById(id);
    }

    /**
     * 根据商户编号,商户订单号,通知类型获取通知记录
     *
     * @param merchantNo      商户编号
     * @param merchantOrderNo 商户订单号
     * @param notifyType      消息类型
     * @return
     */
    @Override
    public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(String merchantNo, String merchantOrderNo, String notifyType) {
        return rpNotifyRecordDao.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(merchantNo,merchantOrderNo,notifyType);
    }

    @Override
    public PageBean<NotifyRecord> queryNotifyRecordListPage(PageParam pageParam, Map<String, Object> paramMap) {
        return rpNotifyRecordDao.listPage(pageParam,paramMap);
    }

    /**
     * 创建消息通知
     *
     * @param notifyRecord
     */
    @Override
    public long createNotifyRecord(NotifyRecord notifyRecord) {
        return rpNotifyRecordDao.insert(notifyRecord);
    }

    /**
     * 修改消息通知
     *
     * @param notifyRecord
     */
    @Override
    public void updateNotifyRecord(NotifyRecord notifyRecord) {
        rpNotifyRecordDao.update(notifyRecord);
    }

    /**
     * 创建消息通知记录
     *
     * @param notifyRecordLog
     * @return
     */
    @Override
    public long createNotifyRecordLog(NotifyRecordLog notifyRecordLog) {
        return rpNotifyRecordLogDao.insert(notifyRecordLog);
    }


}
