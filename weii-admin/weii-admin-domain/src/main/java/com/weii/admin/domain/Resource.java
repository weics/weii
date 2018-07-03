package com.weii.admin.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:20 2018/06/24
 * @Description:
 * @Modified By:
 */
public class Resource extends JSONObject {
    private List<JSONObject> handleList;

    public List<JSONObject> getHandleList() {
        return this.handleList;
    }

    public void setHandleList(final List<JSONObject> handleList) {
        this.handleList = handleList;
    }
}
