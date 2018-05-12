package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.user.entity.RpUserPayInfo;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:36 2018/5/12
 * @Description:
 */
public interface RpUserPayInfoDao extends BaseDao<RpUserPayInfo> {

    /**
     * 通过商户编号获取商户第三方支付信息
     * @param userNo
     * @param payWayCode
     * @return
     */
    public  RpUserPayInfo getByUserNo(String userNo, String payWayCode);

}