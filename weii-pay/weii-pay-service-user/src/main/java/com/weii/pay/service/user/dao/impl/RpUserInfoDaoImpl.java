package com.weii.pay.service.user.dao.impl;

import com.weii.pay.common.core.dao.impl.BaseDaoImpl;
import com.weii.pay.common.core.exception.BizException;
import com.weii.pay.common.core.utils.DateUtils;
import com.weii.pay.service.user.dao.RpUserInfoDao;
import com.weii.pay.service.user.entity.RpUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @Author: weics
 * @Date: Created in 21:49 2018/8/19
 * @Description:  用户信息dao实现类
 * @Modified By:
 */
@Repository
public class RpUserInfoDaoImpl  extends BaseDaoImpl<RpUserInfo> implements RpUserInfoDao {

    /** 用户编号前缀 **/
    private static final String USER_NO_PREFIX = "8888";

    /** 账户编号前缀 **/
    private static final String ACCOUNT_NO_PREFIX = "9999";

    @Override
    public String buildUserNo() {

        // 获取用户编号序列
        String userNoSeq = null;
        String userNo = null;

        try {
            // 获取用户编号序列
            userNoSeq = super.getSqlSession().selectOne(getStatement("buildUserNoSeq"));
            // 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
            String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
            userNo = USER_NO_PREFIX + dateString + userNoSeq.substring(userNoSeq.length() - 8, userNoSeq.length());
        } catch (Exception e) {
            LOG.error("生成用户编号异常：", e);
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        if (StringUtils.isEmpty(userNo)) {
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }

        return userNo;

    }

    @Override
    public String buildAccountNo() {

        // 获取账户编号序列值，用于生成20位的账户编号
        String accountNoSeq = null;
        // 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
        String accountNo = null;

        try {
            // 获取账户编号序列值，用于生成20位的账户编号
            accountNoSeq = super.getSqlSession().selectOne(getStatement("buildAccountNoSeq"));
            // 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
            String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
            accountNo = ACCOUNT_NO_PREFIX + dateString + accountNoSeq.substring(accountNoSeq.length() - 8, accountNoSeq.length());

        } catch (Exception e) {
            LOG.error("生成账户编号异常：", e);
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        if (StringUtils.isEmpty(accountNo)) {
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }

        return accountNo;
    }

}
