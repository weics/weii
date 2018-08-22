package com.weii.pay.service.user.api.impl;


import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.user.api.PayProductService;
import com.weii.pay.service.user.api.PayWayService;
import com.weii.pay.service.user.api.UserPayConfigService;
import com.weii.pay.service.user.api.UserPayInfoService;
import com.weii.pay.service.user.dao.UserPayConfigMapper;
import com.weii.pay.service.user.entity.PayProduct;
import com.weii.pay.service.user.entity.PayWay;
import com.weii.pay.service.user.entity.UserPayConfig;
import com.weii.pay.service.user.entity.UserPayInfo;
import com.weii.pay.service.user.exceptions.PayBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:50 2018/5/12
 * @Description:
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class UserPayConfigServiceImpl implements UserPayConfigService {

    @Autowired
    private UserPayConfigMapper userPayConfigMapper;
    @Autowired
    private PayProductService payProductService;
    @Autowired
    private PayWayService payWayService;
    @Autowired
    private UserPayInfoService userPayInfoService;

    @Override
    public void saveData(UserPayConfig userPayConfig) {
        userPayConfigMapper.insert(userPayConfig);
    }

    @Override
    public void updateData(UserPayConfig userPayConfig) {
        userPayConfigMapper.update(userPayConfig);
    }

    @Override
    public UserPayConfig getDataById(String id) {
        return userPayConfigMapper.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, UserPayConfig userPayConfig) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", userPayConfig.getProductCode());
        paramMap.put("userNo", userPayConfig.getUserNo());
        paramMap.put("userName", userPayConfig.getUserName());
        paramMap.put("productName", userPayConfig.getProductName());
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return userPayConfigMapper.listPage(pageParam, paramMap);
    }

    /**
     * 根据商户编号获取已生效的支付配置
     *
     * @param userNo
     * @return
     */
    @Override
    public UserPayConfig getByUserNo(String userNo) {
        return userPayConfigMapper.getByUserNo(userNo, PublicEnum.YES.name());
    }

    /**
     * 根据商户编号获取支付配置
     * @param userNo
     * @param auditStatus
     * @return
     */
    @Override
    public UserPayConfig getByUserNo(String userNo, String auditStatus){
        return userPayConfigMapper.getByUserNo(userNo, auditStatus);
    }


    /**
     * 根据支付产品获取已生效数据
     */
    @Override
    public List<UserPayConfig> listByProductCode(String productCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", productCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", PublicEnum.YES.name());
        return userPayConfigMapper.listBy(paramMap);
    }

    /**
     * 根据支付产品获取数据
     */
    @Override
    public List<UserPayConfig> listByProductCode(String productCode, String auditStatus){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("productCode", productCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", auditStatus);
        return userPayConfigMapper.listBy(paramMap);
    }

    /**
     * 创建用户支付配置
     */
    @Override
    public void createUserPayConfig(String userNo, String userName, String productCode, String productName, Integer riskDay,
                                    String fundIntoType, String isAutoSett, String appId, String merchantId, String partnerKey,
                                    String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException {

        UserPayConfig payConfig = userPayConfigMapper.getByUserNo(userNo, null);
        if(payConfig != null){
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_EXIST,"用户支付配置已存在");
        }

        UserPayConfig userPayConfig = new UserPayConfig();
        userPayConfig.setUserNo(userNo);
        userPayConfig.setUserName(userName);
        userPayConfig.setProductCode(productCode);
        userPayConfig.setProductName(productName);
        userPayConfig.setStatus(PublicStatusEnum.ACTIVE.name());
        userPayConfig.setAuditStatus(PublicEnum.YES.name());
        userPayConfig.setRiskDay(riskDay);
        userPayConfig.setFundIntoType(fundIntoType);
        userPayConfig.setIsAutoSett(isAutoSett);
        userPayConfig.setPayKey(StringUtil.get32UUID());
        userPayConfig.setPaySecret(StringUtil.get32UUID());
        userPayConfig.setId(StringUtil.get32UUID());
        saveData(userPayConfig);

        //查询支付产品下有哪些支付方式
        List<PayWay> payWayList = payWayService.listByProductCode(productCode);
        Map<String, String> map = new HashMap<String, String>();
        //过滤重复数据
        for(PayWay payWay : payWayList){
            map.put(payWay.getPayWayCode(), payWay.getPayWayName());
        }

        for (String key : map.keySet()) {
            if(key.equals(PayWayEnum.WEIXIN.name())){
                //创建用户第三方支付信息
                UserPayInfo userPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
                if(userPayInfo == null){
                    userPayInfo = new UserPayInfo();
                    userPayInfo.setId(StringUtil.get32UUID());
                    userPayInfo.setCreateTime(new Date());
                    userPayInfo.setAppId(appId);
                    userPayInfo.setMerchantId(merchantId);
                    userPayInfo.setPartnerKey(partnerKey);
                    userPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    userPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    userPayInfo.setUserNo(userNo);
                    userPayInfo.setUserName(userName);
                    userPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    userPayInfoService.saveData(userPayInfo);
                }else{
                    userPayInfo.setEditTime(new Date());
                    userPayInfo.setAppId(appId);
                    userPayInfo.setMerchantId(merchantId);
                    userPayInfo.setPartnerKey(partnerKey);
                    userPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    userPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    userPayInfoService.updateData(userPayInfo);
                }

            }else if(key.equals(PayWayEnum.ALIPAY.name())){
                //创建用户第三方支付信息
                UserPayInfo userPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
                if(userPayInfo == null){
                    userPayInfo = new UserPayInfo();
                    userPayInfo.setId(StringUtil.get32UUID());
                    userPayInfo.setCreateTime(new Date());
                    userPayInfo.setAppId(ali_partner);
                    userPayInfo.setMerchantId(ali_sellerId);
                    userPayInfo.setPartnerKey(ali_key);
                    userPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    userPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    userPayInfo.setUserNo(userNo);
                    userPayInfo.setUserName(userName);
                    userPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    userPayInfoService.saveData(userPayInfo);
                }else{
                    userPayInfo.setEditTime(new Date());
                    userPayInfo.setAppId(ali_partner);
                    userPayInfo.setMerchantId(ali_sellerId);
                    userPayInfo.setPartnerKey(ali_key);
                    userPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    userPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    userPayInfoService.updateData(userPayInfo);
                }
            }
        }



    }

    /**
     * 删除支付产品
     * @param userNo
     */
    @Override
    public void deleteUserPayConfig(String userNo) throws PayBizException{

        UserPayConfig userPayConfig = userPayConfigMapper.getByUserNo(userNo, null);
        if(userPayConfig == null){
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"用户支付配置不存在");
        }

        userPayConfig.setStatus(PublicStatusEnum.UNACTIVE.name());
        userPayConfig.setEditTime(new Date());
        updateData(userPayConfig);
    }

    /**
     * 修改用户支付配置
     */
    @Override
    public void updateUserPayConfig(String userNo, String productCode, String productName, Integer riskDay, String fundIntoType,
                                    String isAutoSett, String appId, String merchantId, String partnerKey,
                                    String ali_partner, String ali_sellerId, String ali_key)  throws PayBizException{
        UserPayConfig userPayConfig = userPayConfigMapper.getByUserNo(userNo, null);
        if(userPayConfig == null){
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"用户支付配置不存在");
        }

        userPayConfig.setProductCode(productCode);
        userPayConfig.setProductName(productName);
        userPayConfig.setRiskDay(riskDay);
        userPayConfig.setFundIntoType(fundIntoType);
        userPayConfig.setIsAutoSett(isAutoSett);
        userPayConfig.setEditTime(new Date());
        updateData(userPayConfig);

        //查询支付产品下有哪些支付方式
        List<PayWay> payWayList = payWayService.listByProductCode(productCode);
        Map<String, String> map = new HashMap<String, String>();
        //过滤重复数据
        for(PayWay payWay : payWayList){
            map.put(payWay.getPayWayCode(), payWay.getPayWayName());
        }

        for (String key : map.keySet()) {
            if(key.equals(PayWayEnum.WEIXIN.name())){
                //创建用户第三方支付信息
                UserPayInfo userPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
                if(userPayInfo == null){
                    userPayInfo = new UserPayInfo();
                    userPayInfo.setId(StringUtil.get32UUID());
                    userPayInfo.setCreateTime(new Date());
                    userPayInfo.setAppId(appId);
                    userPayInfo.setMerchantId(merchantId);
                    userPayInfo.setPartnerKey(partnerKey);
                    userPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    userPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    userPayInfo.setUserNo(userNo);
                    userPayInfo.setUserName(userPayConfig.getUserName());
                    userPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    userPayInfoService.saveData(userPayInfo);
                }else{
                    userPayInfo.setEditTime(new Date());
                    userPayInfo.setAppId(appId);
                    userPayInfo.setMerchantId(merchantId);
                    userPayInfo.setPartnerKey(partnerKey);
                    userPayInfo.setPayWayCode(PayWayEnum.WEIXIN.name());
                    userPayInfo.setPayWayName(PayWayEnum.WEIXIN.getDesc());
                    userPayInfoService.updateData(userPayInfo);
                }

            }else if(key.equals(PayWayEnum.ALIPAY.name())){
                //创建用户第三方支付信息
                UserPayInfo userPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
                if(userPayInfo == null){
                    userPayInfo = new UserPayInfo();
                    userPayInfo.setId(StringUtil.get32UUID());
                    userPayInfo.setCreateTime(new Date());
                    userPayInfo.setAppId(ali_partner);
                    userPayInfo.setMerchantId(ali_sellerId);
                    userPayInfo.setPartnerKey(ali_key);
                    userPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    userPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    userPayInfo.setUserNo(userNo);
                    userPayInfo.setUserName(userPayConfig.getUserName());
                    userPayInfo.setStatus(PublicStatusEnum.ACTIVE.name());
                    userPayInfoService.saveData(userPayInfo);
                }else{
                    userPayInfo.setEditTime(new Date());
                    userPayInfo.setAppId(ali_partner);
                    userPayInfo.setMerchantId(ali_sellerId);
                    userPayInfo.setPartnerKey(ali_key);
                    userPayInfo.setPayWayCode(PayWayEnum.ALIPAY.name());
                    userPayInfo.setPayWayName(PayWayEnum.ALIPAY.getDesc());
                    userPayInfoService.updateData(userPayInfo);
                }
            }
        }
    }

    /**
     * 审核
     * @param userNo
     * @param auditStatus
     */
    @Override
    public void audit(String userNo, String auditStatus){
        UserPayConfig userPayConfig = getByUserNo(userNo, null);
        if(userPayConfig == null){
            throw new PayBizException(PayBizException.USER_PAY_CONFIG_IS_NOT_EXIST,"支付配置不存在！");
        }

        if(auditStatus.equals(PublicEnum.YES.name())){
            //检查是否已关联生效的支付产品
            PayProduct payProduct = payProductService.getByProductCode(userPayConfig.getProductCode(), PublicEnum.YES.name());
            if(payProduct == null){
                throw new PayBizException(PayBizException.PAY_PRODUCT_IS_NOT_EXIST,"未关联已生效的支付产品，无法操作！");
            }

            //检查是否已设置第三方支付信息
        }
        userPayConfig.setAuditStatus(auditStatus);
        userPayConfig.setEditTime(new Date());
        updateData(userPayConfig);
    }

    /**
     * 根据商户key获取已生效的支付配置
     * @param payKey
     * @return
     */
    public UserPayConfig getByPayKey(String payKey){
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("payKey", payKey);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("auditStatus", PublicEnum.YES.name());
        return userPayConfigMapper.getBy(paramMap);
    }
}