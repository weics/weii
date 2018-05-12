package com.weii.pay.service.account.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.account.exceptions.AccountBizException;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:54 2018/5/12
 * @Description: 账户历史service接口
 */
public interface RpAccountHistoryService {
    /**
     * 保存
     */
    void saveData(RpAccountHistory rpAccountHistory) throws AccountBizException;

    /**
     * 更新
     */
    void updateData(RpAccountHistory rpAccountHistory) throws AccountBizException;

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    RpAccountHistory getDataById(String id) throws AccountBizException;


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, RpAccountHistory rpAccountHistory) throws AccountBizException;
}
