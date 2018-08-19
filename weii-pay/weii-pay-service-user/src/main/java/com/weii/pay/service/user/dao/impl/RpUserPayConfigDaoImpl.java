package com.weii.pay.service.user.dao.impl;

import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.service.user.dao.RpUserPayConfigDao;
import com.weii.pay.service.user.entity.RpUserPayConfig;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/8/19
 * @Description: 用户支付配置dao实现类
 * @Modified By:
 */
@Repository
public class RpUserPayConfigDaoImpl  extends BaseDaoImpl<RpUserPayConfig> implements RpUserPayConfigDao {
    @Override
    public RpUserPayConfig getByUserNo(String userNo, String auditStatus) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("userNo",userNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", auditStatus);
        return super.getBy(paramMap);
    }
}
