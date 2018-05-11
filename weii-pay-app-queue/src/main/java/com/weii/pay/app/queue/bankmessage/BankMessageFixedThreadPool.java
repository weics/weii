package com.weii.pay.app.queue.bankmessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 18:27 2018/5/11
 * @Description:
 */
public class BankMessageFixedThreadPool {
    private  static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void addTask(BankMessageTask bankMessageTask){
        fixedThreadPool.execute(bankMessageTask);
    }
}
