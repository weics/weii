package com.weii.pay.service.message.api.impl;

//import com.weii.pay.common.core.enums.PublicEnum;
//import com.weii.pay.common.core.page.PageBean;
//import com.weii.pay.common.core.page.PageParam;
//import com.weii.pay.common.core.utils.PublicConfigUtil;
//import com.weii.pay.common.core.utils.StringUtil;
//import com.weii.pay.service.message.api.TransactionMessageService;
//import com.weii.pay.service.message.api.config.MQSender;
//import com.weii.pay.service.message.dao.RpTransactionMessageDao;
//import com.weii.pay.service.message.entity.TransactionMessage;
//import com.weii.pay.service.message.enums.MessageStatusEnum;
//import com.weii.pay.service.message.exception.MessageBizException;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.PublicConfigUtil;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.message.api.TransactionMessageService;
import com.weii.pay.service.message.api.config.MQSender;
import com.weii.pay.service.message.dao.RpTransactionMessageDao;
import com.weii.pay.service.message.entity.TransactionMessage;
import com.weii.pay.service.message.enums.MessageStatusEnum;
import com.weii.pay.service.message.exception.MessageBizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: weics
 * @Date: Created in 17:37 2018/5/6
 * @Description:
 * @Modified By:
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class TransactionMessageServiceImpl implements TransactionMessageService {

    private static final Log log = LogFactory.getLog(TransactionMessageServiceImpl.class);

    @Autowired
    private RpTransactionMessageDao rpTransactionMessageDao;

    @Autowired
    private MQSender mqSender;


    @Override
    public int saveMessageWaitingConfirm(TransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"保存的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

        message.setEditTime(new Date());
        message.setStatus(MessageStatusEnum.WAITING_CONFIRM.name());
        message.setAreadlyDead(PublicEnum.NO.name());
        message.setMessageSendTimes(0);
        return rpTransactionMessageDao.insert(message);
    }

    @Override
    public void confirmAndSendMessage(String messageId) throws MessageBizException {
        final TransactionMessage message = getMessageByMessageId(messageId);
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }
        message.setStatus(MessageStatusEnum.SENDING.name());
        message.setEditTime(new Date());
        rpTransactionMessageDao.update(message);
        //rabbitmq 发送消息
//        rabbitSender.sendMsg(message);

        mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());
    }

    @Override
    public int saveAndSendMessage(TransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

        message.setStatus(MessageStatusEnum.SENDING.name());
        message.setAreadlyDead(PublicEnum.NO.name());
        message.setMessageSendTimes(0);
        message.setEditTime(new Date());
        int result = rpTransactionMessageDao.insert(message);

//        String uuid = UUID.randomUUID().toString();
//        CorrelationData correlationId = new CorrelationData(uuid);
//        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);



//        amqpTemplate.setDefaultDestinationName(message.getConsumerQueue());
//        notifyJmsTemplate.send(new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                return session.createTextMessage(message.getMessageBody());
//            }
//        });

//        rabbitSender.sendMsg(message);
        mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());
        return result;
    }

    @Override
    public void directSendMessage(final TransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

//        rabbitSender.sendMsg(message);
        mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());
    }

    @Override
    public void reSendMessage(TransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

        message.addSendTimes();
        message.setEditTime(new Date());
        rpTransactionMessageDao.update(message);

//        rabbitSender.sendMsg(message);
        mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());
    }

    @Override
    public void reSendMessageByMessageId(String messageId) throws MessageBizException {
        final TransactionMessage message = getMessageByMessageId(messageId);
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }


        final Integer maxTimes = Integer.valueOf(PublicConfigUtil.readConfig("message.max.send.times"));
        if (message.getMessageSendTimes() >= maxTimes){
            message.setAreadlyDead(PublicEnum.YES.name());
        }

        message.setEditTime(new Date());
        message.setMessageSendTimes(message.getMessageSendTimes()+1);
        rpTransactionMessageDao.update(message);


//        rabbitSender.sendMsg(message);
        mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());

    }

    @Override
    public void setMessageToAreadlyDead(String messageId) throws MessageBizException {
        final TransactionMessage message = getMessageByMessageId(messageId);
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        message.setAreadlyDead(PublicEnum.YES.name());
        message.setEditTime(new Date());
        rpTransactionMessageDao.update(message);
    }

    @Override
    public TransactionMessage getMessageByMessageId(String messageId) throws MessageBizException {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("messageId",messageId);
        return rpTransactionMessageDao.getBy(paramMap);
    }

    @Override
    public void deleteMessageByMessageId(String messageId) throws MessageBizException {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("messageId",messageId);
        rpTransactionMessageDao.delete(paramMap);
    }

    @Override
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException {
        log.info("===>reSendAllDeadMessageByQueueName");

        int numPerPage = 1000;
        if (batchSize > 0 && batchSize <100){
            numPerPage = 100;
        } else if (batchSize > 100 && batchSize < 5000){
            numPerPage = batchSize;
        } else if (batchSize >5000){
            numPerPage = 5000;
        } else {
            numPerPage = 1000;
        }

        int pageNum = 1;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("consumerQueue",queueName);
        paramMap.put("areadlyDead",PublicEnum.YES.name());
        paramMap.put("listPageSortType","ASC");

        Map<String,TransactionMessage> messageMap = new HashMap<>();
        List<Object> recordList = new ArrayList<>();
        int pageCount = 1;

        PageBean pageBean = rpTransactionMessageDao.listPage(new PageParam(pageNum,numPerPage),paramMap);
        recordList = pageBean.getRecordList();
        if (recordList == null || recordList.isEmpty()){
            log.info("=====>recordList is empty");
            return ;
        }


        pageCount = pageBean.getTotalPage();
        for (final Object obj : recordList){
            final TransactionMessage message = (TransactionMessage)obj;
            messageMap.put(message.getMessageId(),message);
        }


        recordList = null;
        pageBean = null;

        for (Map.Entry<String,TransactionMessage> entry: messageMap.entrySet()){
            final TransactionMessage message = entry.getValue();
            message.setEditTime(new Date());
            message.setMessageSendTimes(message.getMessageSendTimes()+1);
            rpTransactionMessageDao.update(message);
            mqSender.sendQueue(message.getConsumerQueue(),message.getMessageBody());
        }



    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws MessageBizException {
        return rpTransactionMessageDao.listPage(pageParam, paramMap);
    }
}
