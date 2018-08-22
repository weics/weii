package com.weii.pay.service.user.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.UserPayConfig;
import com.weii.pay.service.user.exceptions.PayBizException;

import java.util.List;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:26 2018/5/12
 * @Description: 用户支付配置service接口
 */
public interface UserPayConfigService {
    /**
     * 保存
     */
    void saveData(UserPayConfig userPayConfig) throws PayBizException;

    /**
     * 更新
     */
    void updateData(UserPayConfig userPayConfig) throws PayBizException;

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    UserPayConfig getDataById(String id) throws PayBizException;


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, UserPayConfig userPayConfig) throws PayBizException;

    /**
     * 根据商户编号获取已生效的支付配置
     * @param userNo
     * @return
     */
    UserPayConfig getByUserNo(String userNo) throws PayBizException;

    /**
     * 根据商户编号获取支付配置
     * @param userNo
     * @param auditStatus
     * @return
     */
    UserPayConfig getByUserNo(String userNo, String auditStatus) throws PayBizException;

    /**
     * 根据支付产品获取已生效数据
     */
    List<UserPayConfig> listByProductCode(String productCode) throws PayBizException;

    /**
     * 根据支付产品获取数据
     */
    List<UserPayConfig> listByProductCode(String productCode, String auditStatus) throws PayBizException;

    /**
     * 创建用户支付配置
     */
    void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay, String fundIntoType,
                             String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException;

    /**
     * 删除支付产品
     * @param userNo
     */
    void deleteUserPayConfig(String userNo) throws PayBizException;

    /**
     * 修改用户支付配置
     */
    void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay, String fundIntoType,
                             String isAutoSett, String appId, String merchantId, String partnerKey, String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException;

    /**
     * 审核
     * @param userNo
     * @param auditStatus
     */
    void audit(String userNo, String auditStatus) throws PayBizException;

    /**
     * 根据商户key获取已生效的支付配置
     * @param payKey
     * @return
     */
    UserPayConfig getByPayKey(String payKey) throws PayBizException;
}
