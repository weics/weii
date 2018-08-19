package com.weii.pay.service.user.dao.impl;

import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.service.user.dao.RpUserPayInfoDao;
import com.weii.pay.service.user.entity.RpUserPayInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/8/19
 * @Description: 用户第三方支付信息dao实现类
 * @Modified By:
 */
@Repository
public class RpUserPayInfoDaoImpl  extends BaseDaoImpl<RpUserPayInfo> implements RpUserPayInfoDao {
    /**
     * 通过商户编号获取商户第三方支付信息
     *
     * @param userNo
     * @param payWayCode
     * @return
     */
    @Override
    public RpUserPayInfo getByUserNo(String userNo, String payWayCode) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("userNo",userNo);
        paramMap.put("payWayCode",payWayCode);
        return super.getBy(paramMap);
    }
}
