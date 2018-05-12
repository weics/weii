package com.weii.pay.app.queue;

import com.weii.pay.app.queue.utils.SpringContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 18:43 2018/5/11
 * @Description: 通知APP.
 */
public class App {
    private static final Log LOG = LogFactory.getLog(App.class);

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring/spring-context.xml" });
            // 初始化SpringContextUtil
            SpringContextUtil ctxUtil = new SpringContextUtil();
            ctxUtil.setApplicationContext(context);

            context.start();
            LOG.info("== context start");

        } catch (Exception e) {
            LOG.error("== application start error:", e);
            return;
        }

        synchronized (App.class) {
            while (true) {
                try {
                    App.class.wait();
                } catch (InterruptedException e) {
                    LOG.error("== synchronized error:", e);
                }
            }
        }
    }
}
