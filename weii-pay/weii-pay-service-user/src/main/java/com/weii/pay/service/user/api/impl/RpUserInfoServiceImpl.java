package com.weii.pay.service.user.api.impl;


import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.exception.BizException;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.DateUtils;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.account.entity.RpAccount;
import com.weii.pay.service.user.api.RpUserInfoService;
import com.weii.pay.service.user.dao.RpUserInfoMapper;
import com.weii.pay.service.user.entity.RpUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:48 2018/5/12
 * @Description:
 */
@com.alibaba.dubbo.config.annotation.Service(

        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"

)
@Service
public class RpUserInfoServiceImpl implements RpUserInfoService {

    /** 用户编号前缀 **/
    private static final String USER_NO_PREFIX = "8888";

    /** 账户编号前缀 **/
    private static final String ACCOUNT_NO_PREFIX = "9999";


    @Autowired
    private RpUserInfoMapper rpUserInfoMapper;

//    @Autowired
//    private RpAccountService rpAccountService;

    @Override
    public void saveData(RpUserInfo rpUserInfo) {
        rpUserInfoMapper.insert(rpUserInfo);
    }

    @Override
    public void updateData(RpUserInfo rpUserInfo) {
        rpUserInfoMapper.update(rpUserInfo);
    }

    @Override
    public RpUserInfo getDataById(String id) {
        return rpUserInfoMapper.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, RpUserInfo rpUserInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return rpUserInfoMapper.listPage(pageParam, paramMap);
    }

    /**
     * 用户线下注册
     *
     * @param userName
     *            用户名
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerOffline(String userName) {

        String userNo = buildUserNo();
        String accountNo = buildAccountNo();

        //生成用户信息
        RpUserInfo rpUserInfo = new RpUserInfo();
        rpUserInfo.setAccountNo(accountNo);
        rpUserInfo.setCreateTime(new Date());
        rpUserInfo.setId(StringUtil.get32UUID());
        rpUserInfo.setStatus(PublicStatusEnum.ACTIVE.name());
        rpUserInfo.setUserName(userName);
        rpUserInfo.setUserNo(userNo);
        rpUserInfo.setVersion(0);
        this.saveData(rpUserInfo);

        // 生成账户信息
        RpAccount rpAccount = new RpAccount();
        rpAccount.setAccountNo(accountNo);
        rpAccount.setAccountType("0");
        rpAccount.setCreateTime(new Date());
        rpAccount.setEditTime(new Date());
        rpAccount.setId(StringUtil.get32UUID());
        rpAccount.setStatus(PublicStatusEnum.ACTIVE.name());
        rpAccount.setUserNo(userNo);
        rpAccount.setBalance(new BigDecimal("0"));
        rpAccount.setSecurityMoney(new BigDecimal("0"));
        rpAccount.setSettAmount(new BigDecimal("0"));
        rpAccount.setTodayExpend(new BigDecimal("0"));
        rpAccount.setTodayIncome(new BigDecimal("0"));
        rpAccount.setUnbalance(new BigDecimal("0"));
        rpAccount.setTotalExpend(new BigDecimal("0"));
        rpAccount.setTotalIncome(new BigDecimal("0"));
//        rpAccountService.saveData(rpAccount);
    }



    /**
     * 根据商户编号获取商户信息
     *
     * @param merchantNo
     * @return
     */
    @Override
    public RpUserInfo getDataByMerchentNo(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", merchantNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return rpUserInfoMapper.getBy(paramMap);
    }

    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<RpUserInfo> listAll(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return rpUserInfoMapper.listBy(paramMap);
    }



    private String buildAccountNo() {
        // 获取账户编号序列值，用于生成20位的账户编号
        String accountNoSeq = null;
        // 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
        String accountNo = null;

        try {
            // 获取账户编号序列值，用于生成20位的账户编号
            accountNoSeq = rpUserInfoMapper.buildAccountNo();
            // 20位的账户编号规范：'9999' + yyyyMMdd(时间) + 序列的后8位
            String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
            accountNo = ACCOUNT_NO_PREFIX + dateString + accountNoSeq.substring(accountNoSeq.length() - 8, accountNoSeq.length());

        } catch (Exception e) {
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        if (StringUtils.isEmpty(accountNo)) {
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }

        return accountNo;


    }

    private String buildUserNo() {
        // 获取用户编号序列
        String userNoSeq = null;
        String userNo = null;

        try {
            // 获取用户编号序列
            userNoSeq = rpUserInfoMapper.buildUserNo();
            // 20位的用户编号规范：'8888' + yyyyMMdd(时间) + 序列的后8位
            String dateString = DateUtils.toString(new Date(), "yyyyMMdd");
            userNo = USER_NO_PREFIX + dateString + userNoSeq.substring(userNoSeq.length() - 8, userNoSeq.length());
        } catch (Exception e) {

            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        if (StringUtils.isEmpty(userNo)) {
            throw BizException.DB_GET_SEQ_NEXT_VALUE_ERROR;
        }
        return userNo;
    }
}