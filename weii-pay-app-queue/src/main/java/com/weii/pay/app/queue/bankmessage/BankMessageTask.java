package com.weii.pay.app.queue.bankmessage;

import com.weii.pay.app.queue.bankmessage.biz.BankMessageBiz;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 18:27 2018/5/11
 * @Description:
 */
public class BankMessageTask implements Runnable {
    private static final Log LOG = LogFactory.getLog(BankMessageTask.class);

    @Autowired
    private BankMessageBiz bankMessageBiz;

    private Map<String , String> notifyMessageMap;

    public BankMessageTask(Map<String , String> notifyMessageMap){
        this.notifyMessageMap = notifyMessageMap;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        bankMessageBiz.completePay(notifyMessageMap);

    }

    public Map<String, String> getNotifyMessageMap() {
        return notifyMessageMap;
    }

    public void setNotifyMessageMap(Map<String, String> notifyMessageMap) {
        this.notifyMessageMap = notifyMessageMap;
    }

    public BankMessageBiz getBankMessageBiz() {
        return bankMessageBiz;
    }

    public void setBankMessageBiz(BankMessageBiz bankMessageBiz) {
        this.bankMessageBiz = bankMessageBiz;
    }
}
