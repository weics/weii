package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.user.entity.RpUserPayConfig;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:36 2018/5/12
 * @Description:
 */
public interface RpUserPayConfigDao extends BaseDao<RpUserPayConfig> {

    /**
     * 根据用户编号获取用户支付信息
     * @param userNo
     * @return
     */
    RpUserPayConfig getByUserNo(String userNo, String auditStatus);

}