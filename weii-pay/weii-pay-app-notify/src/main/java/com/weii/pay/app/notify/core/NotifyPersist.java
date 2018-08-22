package com.weii.pay.app.notify.core;

import com.weii.pay.service.notify.api.NotifyService;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.entity.NotifyRecordLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: weics
 * @Date: Created in 13:35 2018/5/13
 * @Description:
 * @Modified By:
 */
@Service
public class NotifyPersist {
    @Autowired
    private NotifyService notifyService;

    /**
     * 创建商户通知记录.<br/>
     *
     * @param notifyRecord
     * @return
     */
    public long saveNotifyRecord(NotifyRecord notifyRecord) {
        return notifyService.createNotifyRecord(notifyRecord);
    }

    /**
     * 更新商户通知记录.<br/>
     *
     * @param id
     * @param notifyTimes
     *            通知次数.<br/>
     * @param status
     *            通知状态.<br/>
     * @return 更新结果
     */
    public  void updateNotifyRord(String id, int notifyTimes, String status) {
        NotifyRecord notifyRecord = notifyService.getNotifyRecordById(id);
        notifyRecord.setNotifyTimes(notifyTimes);
        notifyRecord.setStatus(status);
        notifyRecord.setLastNotifyTime(new Date());
        notifyService.updateNotifyRecord(notifyRecord);
    }

    /**
     * 创建商户通知日志记录.<br/>
     *
     * @param notifyId
     *            通知记录ID.<br/>
     * @param merchantNo
     *            商户编号.<br/>
     * @param merchantOrderNo
     *            商户订单号.<br/>
     * @param request
     *            请求信息.<br/>
     * @param response
     *            返回信息.<br/>
     * @param httpStatus
     *            通知状态(HTTP状态).<br/>
     * @return 创建结果
     */
    public long saveNotifyRecordLogs(String notifyId, String merchantNo, String merchantOrderNo, String request, String response,
                                     int httpStatus) {
        NotifyRecordLog notifyRecordLog = new NotifyRecordLog();
        notifyRecordLog.setHttpStatus(httpStatus);
        notifyRecordLog.setMerchantNo(merchantNo);
        notifyRecordLog.setMerchantOrderNo(merchantOrderNo);
        notifyRecordLog.setNotifyId(notifyId);
        notifyRecordLog.setRequest(request);
        notifyRecordLog.setResponse(response);
        notifyRecordLog.setCreateTime(new Date());
        notifyRecordLog.setEditTime(new Date());
        return notifyService.createNotifyRecordLog(notifyRecordLog);
    }
}
