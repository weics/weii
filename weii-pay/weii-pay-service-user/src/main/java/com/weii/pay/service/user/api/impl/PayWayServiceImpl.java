package com.weii.pay.service.user.api.impl;

import com.weii.pay.common.core.enums.PayTypeEnum;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.user.api.PayProductService;
import com.weii.pay.service.user.api.PayWayService;
import com.weii.pay.service.user.dao.PayWayMapper;
import com.weii.pay.service.user.entity.PayProduct;
import com.weii.pay.service.user.entity.PayWay;
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
 * @date Created in 10:46 2018/5/12
 * @Description:
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class PayWayServiceImpl implements PayWayService {

    @Autowired
    private PayWayMapper payWayMapper;

    @Autowired
    private PayProductService payProductService;

    @Override
    public void saveData(PayWay payWay) {
        payWayMapper.insert(payWay);
    }

    @Override
    public void updateData(PayWay payWay) {
        payWayMapper.update(payWay);
    }

    @Override
    public PayWay getDataById(String id) {
        return payWayMapper.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, PayWay payWay) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("payProductCode", payWay.getPayProductCode());
        paramMap.put("payWayName", payWay.getPayWayName());
        paramMap.put("payTypeName", payWay.getPayTypeName());
        return payWayMapper.listPage(pageParam, paramMap);
    }

    @Override
    public PayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String payTypeCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("payTypeCode", payTypeCode);
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return payWayMapper.getBy(paramMap);
    }

    /**
     * 绑定支付费率
     * @param payWayCode
     * @param payTypeCode
     * @param payRate
     */
    @Override
    public void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate) throws PayBizException {
        PayWay payWay = getByPayWayTypeCode(payProductCode,payWayCode,payTypeCode);
        if(payWay!=null){
            throw new PayBizException(PayBizException.PAY_TYPE_IS_EXIST,"支付渠道已存在");
        }

        PayProduct payProduct = payProductService.getByProductCode(payProductCode, null);
        if(payProduct.getAuditStatus().equals(PublicEnum.YES.name())){
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法绑定！");
        }

        PayWay rpPayWay = new PayWay();
        rpPayWay.setPayProductCode(payProductCode);
        rpPayWay.setPayRate(payRate);
        rpPayWay.setPayWayCode(payWayCode);
        rpPayWay.setPayWayName(PayWayEnum.getEnum(payWayCode).getDesc());
        rpPayWay.setPayTypeCode(payTypeCode);
        rpPayWay.setPayTypeName(PayTypeEnum.getEnum(payTypeCode).getDesc());
        rpPayWay.setStatus(PublicStatusEnum.ACTIVE.name());
        rpPayWay.setCreateTime(new Date());
        rpPayWay.setId(StringUtil.get32UUID());
        saveData(rpPayWay);
    }

    /**
     * 根据支付产品获取支付方式
     */
    @Override
    public List<PayWay> listByProductCode(String payProductCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return payWayMapper.listBy(paramMap);
    }

    /**
     * 获取所有支付方式
     */
    @Override
    public List<PayWay> listAll(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return payWayMapper.listBy(paramMap);
    }
}