package com.weii.pay.app.notify.entity;

import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 14:28 2018/5/13
 * @Description:
 * @Modified By:
 */
public class NotifyParam {

    private Map<Integer, Integer> notifyParams;// 通知时间次数map
    private String successValue;// 通知后用于判断是否成功的返回值。由HttpResponse获取

    public Map<Integer, Integer> getNotifyParams() {
        return notifyParams;
    }

    public void setNotifyParams(Map<Integer, Integer> notifyParams) {
        this.notifyParams = notifyParams;
    }

    public String getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(String successValue) {
        this.successValue = successValue;
    }

    public Integer getMaxNotifyTime() {
        return notifyParams == null ? 0 : notifyParams.size();
    }
}
