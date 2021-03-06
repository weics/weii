package com.weii.pay.service.message.api;

import com.github.pagehelper.PageInfo;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.message.entity.TransactionMessage;
import com.weii.pay.service.message.exception.MessageBizException;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 17:32 2018/5/6
 * @Description: message状态枚举.
 * @Modified By:
 */
public interface TransactionMessageService {

    /**
     * 预存储消息.
     */
    public int saveMessageWaitingConfirm(TransactionMessage transactionMessage) throws MessageBizException;


    /**
     * 确认并发送消息.
     */
    public void confirmAndSendMessage(String messageId) throws MessageBizException;


    /**
     * 存储并发送消息.
     */
    public int saveAndSendMessage(TransactionMessage transactionMessage) throws MessageBizException;


    /**
     * 直接发送消息.
     */
    public void directSendMessage(TransactionMessage transactionMessage) throws MessageBizException;


    /**
     * 重发消息.
     */
    public void reSendMessage(TransactionMessage transactionMessage) throws MessageBizException;


    /**
     * 根据messageId重发某条消息.
     */
    public void reSendMessageByMessageId(String messageId) throws MessageBizException;


    /**
     * 将消息标记为死亡消息.
     */
    public void setMessageToAreadlyDead(String messageId) throws MessageBizException;


    /**
     * 根据消息ID获取消息
     */
    public TransactionMessage getMessageByMessageId(String messageId) throws MessageBizException;

    /**
     * 根据消息ID删除消息
     */
    public void deleteMessageByMessageId(String messageId) throws MessageBizException;


    /**
     * 重发某个消息队列中的全部已死亡的消息.
     */
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) throws MessageBizException;

    /**
     * 获取分页数据
     */
    PageInfo<TransactionMessage> listPage(PageParam pageParam, Map<String, Object> paramMap) throws MessageBizException;
}
