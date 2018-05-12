package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.user.entity.RpUserInfo;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:36 2018/5/12
 * @Description:
 */
public interface RpUserInfoDao extends BaseDao<RpUserInfo> {

    /** 获取用户编号 **/
    String buildUserNo();

    /** 获取账户编号 **/
    String buildAccountNo();
}