package com.weii.pay.app.notify.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weii.pay.app.notify.core.NotifyPersist;
import com.weii.pay.app.notify.core.NotifyQueue;
import com.weii.pay.common.core.exception.BizException;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.notify.api.NotifyService;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.enums.NotifyStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: weics
 * @Date: Created in 15:00 2018/5/13
 * @Description:
 * @Modified By:
 */
@Component
@RabbitListener(queues = "hello")
public class ConsumerSessionAwareMessageReceiver {

    private static final Log log = LogFactory.getLog(ConsumerSessionAwareMessageReceiver.class);

    @Autowired
    private NotifyQueue notifyQueue;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private NotifyPersist notifyPersist;

    @RabbitHandler
    public void process(Object message) {
        System.out.println("Receiver  : " + message.toString());



        try {
//            ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
            final String ms = message.toString();
            log.info("== receive message:" + ms);

            JSON json = (JSON) JSONObject.parse(ms);
            NotifyRecord notifyRecord = JSONObject.toJavaObject(json, NotifyRecord.class);
            if (notifyRecord == null) {
                return;
            }
            // log.info("notifyParam:" + notifyParam);
            notifyRecord.setStatus(NotifyStatusEnum.CREATED.name());
            notifyRecord.setCreateTime(new Date());
            notifyRecord.setLastNotifyTime(new Date());

            if ( !StringUtil.isEmpty(notifyRecord.getId())){
                NotifyRecord notifyRecordById = notifyService.getNotifyRecordById(notifyRecord.getId());
                if (notifyRecordById != null){
                    return;
                }
            }

            while (notifyService == null) {
                Thread.currentThread().sleep(1000); // 主动休眠，防止类Spring 未加载完成，监听服务就开启监听出现空指针异常
            }

            try {
                // 将获取到的通知先保存到数据库中
                notifyPersist.saveNotifyRecord(notifyRecord);
                notifyRecord = notifyService.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(notifyRecord.getMerchantNo(), notifyRecord.getMerchantOrderNo(), notifyRecord.getNotifyType());

                // 添加到通知队列
                notifyQueue.addElementToList(notifyRecord);
            }  catch (BizException e) {
                log.error("BizException :", e);
            } catch (Exception e) {
                log.error(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }


    }
}
