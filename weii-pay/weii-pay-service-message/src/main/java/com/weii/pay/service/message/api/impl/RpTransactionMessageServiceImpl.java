package com.weii.pay.service.message.api.impl;

import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.PublicConfigUtil;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.message.api.RpTransactionMessageService;
import com.weii.pay.service.message.api.config.RabbitMQConfig;
import com.weii.pay.service.message.dao.RpTransactionMessageDao;
import com.weii.pay.service.message.entity.RpTransactionMessage;
import com.weii.pay.service.message.enums.MessageStatusEnum;
import com.weii.pay.service.message.exception.MessageBizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: weics
 * @Date: Created in 17:37 2018/5/6
 * @Description:
 * @Modified By:
 */
@Service
public class RpTransactionMessageServiceImpl implements RpTransactionMessageService {

    private static final Log log = LogFactory.getLog(RpTransactionMessageServiceImpl.class);

    @Autowired
    private RpTransactionMessageDao rpTransactionMessageDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    public int saveMessageWaitingConfirm(RpTransactionMessage message) throws MessageBizException {
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
        final RpTransactionMessage message = getMessageByMessageId(messageId);
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }
        message.setStatus(MessageStatusEnum.SENDING.name());
        message.setEditTime(new Date());
//        rpTransactionMessageDao.update(message);
//        rabbitTemplate.send(new Message);

        //rabbitmq 发送消息

        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);


    }

    @Override
    public int saveAndSendMessage(RpTransactionMessage message) throws MessageBizException {
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

        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);


        return result;
    }

    @Override
    public void directSendMessage(final RpTransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);



    }

    @Override
    public void reSendMessage(RpTransactionMessage message) throws MessageBizException {
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        if (StringUtil.isEmpty(message.getConsumerQueue())){
            throw new MessageBizException(MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,"消息的消费队列不能为空");
        }

        message.addSendTimes();
        message.setEditTime(new Date());
        rpTransactionMessageDao.update(message);

        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);

    }

    @Override
    public void reSendMessageByMessageId(String messageId) throws MessageBizException {
        final RpTransactionMessage message = getMessageByMessageId(messageId);
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


        String uuid = UUID.randomUUID().toString();
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);



    }

    @Override
    public void setMessageToAreadlyDead(String messageId) throws MessageBizException {
        final RpTransactionMessage message = getMessageByMessageId(messageId);
        if (message == null){
            throw new MessageBizException(MessageBizException.SAVA_MESSAGE_IS_NULL,"根据id查找的消息为空");
        }

        message.setAreadlyDead(PublicEnum.YES.name());
        message.setEditTime(new Date());
        rpTransactionMessageDao.update(message);
    }

    @Override
    public RpTransactionMessage getMessageByMessageId(String messageId) throws MessageBizException {
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

        Map<String,RpTransactionMessage> messageMap = new HashMap<>();
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
            final RpTransactionMessage message = (RpTransactionMessage)obj;
            messageMap.put(message.getMessageId(),message);
        }


        recordList = null;
        pageBean = null;

        for (Map.Entry<String,RpTransactionMessage> entry: messageMap.entrySet()){
            final RpTransactionMessage message = entry.getValue();
            message.setEditTime(new Date());
            message.setMessageSendTimes(message.getMessageSendTimes()+1);
            rpTransactionMessageDao.update(message);



            String uuid = UUID.randomUUID().toString();
            CorrelationData correlationId = new CorrelationData(uuid);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, message,correlationId);


        }



    }

    @Override
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws MessageBizException {
        return rpTransactionMessageDao.listPage(pageParam, paramMap);
    }
}
