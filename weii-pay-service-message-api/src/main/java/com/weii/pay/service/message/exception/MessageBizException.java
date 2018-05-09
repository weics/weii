package com.weii.pay.service.message.exception;

import com.weii.pay.common.core.exception.BizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: weics
 * @Date: Created in 17:32 2018/5/6
 * @Description: 消息模块业务异常类
 * @Modified By:
 */
public class MessageBizException extends BizException {
    /**
     *
     */
    private static final long serialVersionUID = 9113965268385227614L;
    /** 保存的消息为空 **/
    public static final int SAVA_MESSAGE_IS_NULL = 8001;

    /** 消息的消费队列为空 **/
    public static final int MESSAGE_CONSUMER_QUEUE_IS_NULL = 8002;

    private static final Log LOG = LogFactory.getLog(MessageBizException.class);


    public MessageBizException() {
    }

    public MessageBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public MessageBizException(int code, String msg) {
        super(code, msg);
    }

    public MessageBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
