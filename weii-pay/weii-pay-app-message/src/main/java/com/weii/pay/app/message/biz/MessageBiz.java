package com.weii.pay.app.message.biz;

import com.weii.pay.common.core.utils.PublicConfigUtil;
import com.weii.pay.service.message.api.TransactionMessageService;
import com.weii.pay.service.message.entity.TransactionMessage;
import com.weii.pay.service.message.exception.MessageBizException;
import com.weii.pay.service.trade.api.TradePaymentQueryService;
import com.weii.pay.service.trade.entity.TradePaymentRecord;
import com.weii.pay.service.trade.enums.TradeStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:59 2018/5/6
 * @Description: message业务处理类
 * @Modified By:
 */
public class MessageBiz {

    private static final Log log = LogFactory.getLog(MessageBiz.class);

    @Autowired
    private TradePaymentQueryService tradePaymentQueryService;
    @Autowired
    private TransactionMessageService transactionMessageService;

    /**
     * 处理[waiting_confirm]状态的消息
     * @param messageMap
     */
    public void handleWaitingConfirmTimeOutMessage(Map<String,TransactionMessage> messageMap){
        // 单条消息处理（目前该状态的消息，消费队列全部是accounting，如果后期有业务扩充，需做队列判断，做对应的业务处理。）
        for (Map.Entry<String,TransactionMessage> entry : messageMap.entrySet()){
            TransactionMessage message = entry.getValue();
            try {
                log.debug("开始处理[waiting_confirm]消息ID为[" + message.getMessageId() + "]的消息");
                String bankOrderNo = message.getField1();

                TradePaymentRecord record = tradePaymentQueryService.getRecordByBankOrderNo(bankOrderNo);
                // 如果订单成功，把消息改为待处理，并发送消息
                if (TradeStatusEnum.SUCCESS.name().equals(record.getStatus())){
                    // 确认并发送消息
                    transactionMessageService.confirmAndSendMessage(message.getMessageId());
                } else if (TradeStatusEnum.WAITING_PAYMENT.name().equals(record.getStatus())){
                    // 订单状态是等到支付，可以直接删除数据
                    log.debug("订单没有支付成功,删除[waiting_confirm]消息id[" + message.getMessageId() + "]的消息");
                    transactionMessageService.deleteMessageByMessageId(message.getMessageId());
                }

                log.debug("结束处理[waiting_confirm]消息ID为[" + message.getMessageId() + "]的消息");
            } catch (MessageBizException e) {
                e.printStackTrace();
                log.error("处理[waiting_confirm]消息ID为[" + message.getMessageId() + "]的消息异常：", e);
            }

        }

    }


    /**
     * 处理[SENDING]状态的消息
     *
     * @param messageMap
     */
    public void handleSendingTimeOutMessage(Map<String, TransactionMessage> messageMap){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.debug("开始处理[SENDING]状态的消息,总条数[" + messageMap.size() + "]");

        // 根据配置获取通知间隔时间
        Map<Integer, Integer> notifyParam = getSendTime();

        // 单条消息处理
        for (Map.Entry<String,TransactionMessage> entry : messageMap.entrySet()){
            TransactionMessage message = entry.getValue();

            log.debug("开始处理[SENDING]消息ID为[" + message.getMessageId() + "]的消息");
            //判断发送的次数
            int maxTimes = Integer.valueOf(PublicConfigUtil.readConfig("message.max.send.times"));
            log.debug("[SENDING]消息ID为[" + message.getMessageId() + "]的消息,已经重新发送的次数[" + message.getMessageSendTimes() + "]");
            // 如果超过最大发送次数直接退出
            if (maxTimes < message.getMessageSendTimes()){
                //标记为死亡
                transactionMessageService.setMessageToAreadlyDead(message.getMessageId());
                continue;
            }

            // 判断是否达到发送消息的时间间隔条件
            int reSendTimes = message.getMessageSendTimes();
            int times = notifyParam.get(reSendTimes == 0 ? 1 : reSendTimes);
            long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
            long needTime = currentTimeInMillis - times * 60 * 1000;
            long hasTime = message.getEditTime().getTime();
            // 判断是否达到了可以再次发送的时间条件
            if (hasTime > needTime) {
                log.debug("currentTime[" + sdf.format(new Date()) + "],[SENDING]消息上次发送时间[" + sdf.format(message.getEditTime()) + "],必须过了[" + times + "]分钟才可以再发送。");
                continue;
            }

            // 重新发送消息
            transactionMessageService.reSendMessage(message);
            log.debug("结束处理[SENDING]消息ID为[" + message.getMessageId() + "]的消息");

        }


    }




    /**
     * 根据配置获取通知间隔时间
     *
     * @return
     */
    private Map<Integer, Integer> getSendTime() {
        Map<Integer, Integer> notifyParam = new HashMap<Integer, Integer>();
        notifyParam.put(1, Integer.valueOf(PublicConfigUtil.readConfig("message.send.1.time")));
        notifyParam.put(2, Integer.valueOf(PublicConfigUtil.readConfig("message.send.2.time")));
        notifyParam.put(3, Integer.valueOf(PublicConfigUtil.readConfig("message.send.3.time")));
        notifyParam.put(4, Integer.valueOf(PublicConfigUtil.readConfig("message.send.4.time")));
        notifyParam.put(5, Integer.valueOf(PublicConfigUtil.readConfig("message.send.5.time")));
        return notifyParam;
    }


}
