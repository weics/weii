package com.weii.pay.app.queue.bankmessage.biz;



import com.weii.pay.service.message.api.RpTransactionMessageService;
import com.weii.pay.service.trade.api.RpTradePaymentManagerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 18:28 2018/5/11
 * @Description:
 */
public class BankMessageBiz {
    private static final Log LOG = LogFactory.getLog(BankMessageBiz.class);

    @Autowired
    private RpTradePaymentManagerService rpTradePaymentManagerService;

    @Autowired
    private RpTransactionMessageService rpTransactionMessageService;

    public void completePay(Map<String , String > notifyMessageMap){

        String messageId = notifyMessageMap.get("messageId");
        String payWayCode = notifyMessageMap.get("payWayCode");
        //调用业务方法,完成交易
        try{

            rpTradePaymentManagerService.completeScanPay(payWayCode, notifyMessageMap);

            //删除消息
            rpTransactionMessageService.deleteMessageByMessageId(messageId);
        }catch (Exception e){
            LOG.error("完成支付结果异常:",e);
        }

    }

    public RpTradePaymentManagerService getRpTradePaymentManagerService() {
        return rpTradePaymentManagerService;
    }

    public void setRpTradePaymentManagerService(RpTradePaymentManagerService rpTradePaymentManagerService) {
        this.rpTradePaymentManagerService = rpTradePaymentManagerService;
    }

    public RpTransactionMessageService getRpTransactionMessageService() {
        return rpTransactionMessageService;
    }

    public void setRpTransactionMessageService(RpTransactionMessageService rpTransactionMessageService) {
        this.rpTransactionMessageService = rpTransactionMessageService;
    }
}
