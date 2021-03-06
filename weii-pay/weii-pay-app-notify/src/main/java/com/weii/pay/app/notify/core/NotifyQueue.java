package com.weii.pay.app.notify.core;

import com.weii.pay.app.notify.App;
import com.weii.pay.app.notify.entity.NotifyParam;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.enums.NotifyStatusEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 14:27 2018/5/13
 * @Description:
 * @Modified By:
 */
@Component
public class NotifyQueue implements Serializable {



    private static final Log LOG = LogFactory.getLog(NotifyQueue.class);

    @Autowired
    private NotifyParam notifyParam;

    @Autowired
    private NotifyPersist notifyPersist;
    /**
     * 将传过来的对象进行通知次数判断，之后决定是否放在任务队列中
     *
     * @param notifyRecord
     * @throws Exception
     */
    public void addElementToList(NotifyRecord notifyRecord) {
        if (notifyRecord == null) {
            return;
        }
        Integer notifyTimes = notifyRecord.getNotifyTimes(); // 通知次数
        Integer maxNotifyTime = 0;
        try {
            maxNotifyTime = notifyParam.getMaxNotifyTime();
        } catch (Exception e) {
            LOG.error(e);
        }
        if (notifyRecord.getVersion().intValue() == 0) {// 刚刚接收到的数据
            notifyRecord.setLastNotifyTime(new Date());
        }
        long time = notifyRecord.getLastNotifyTime().getTime();
        Map<Integer, Integer> timeMap = notifyParam.getNotifyParams();
        if (notifyTimes < maxNotifyTime) {
            Integer nextKey = notifyTimes + 1;
            Integer next = timeMap.get(nextKey);
            if (next != null) {
                time += 1000 * 60 * next + 1;
                notifyRecord.setLastNotifyTime(new Date(time));
                App.tasks.put(new NotifyTask(notifyRecord, this, notifyParam));
            }
        } else {
            try {
                // 持久化到数据库中
                notifyPersist.updateNotifyRord(notifyRecord.getId(),
                        notifyRecord.getNotifyTimes(), NotifyStatusEnum.FAILED.name());
                LOG.info("Update NotifyRecord failed,merchantNo:" + notifyRecord.getMerchantNo() + ",merchantOrderNo:"
                        + notifyRecord.getMerchantOrderNo());
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }
}
