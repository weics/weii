package com.weii.pay.service.user.api.impl;

import com.weii.pay.common.core.enums.PayTypeEnum;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.user.api.RpPayProductService;
import com.weii.pay.service.user.api.RpPayWayService;
import com.weii.pay.service.user.dao.RpPayWayDao;
import com.weii.pay.service.user.entity.RpPayProduct;
import com.weii.pay.service.user.entity.RpPayWay;
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
public class RpPayWayServiceImpl implements RpPayWayService {

    @Autowired
    private RpPayWayDao rpPayWayDao;

    @Autowired
    private RpPayProductService rpPayProductService;

    @Override
    public void saveData(RpPayWay rpPayWay) {
        rpPayWayDao.insert(rpPayWay);
    }

    @Override
    public void updateData(RpPayWay rpPayWay) {
        rpPayWayDao.update(rpPayWay);
    }

    @Override
    public RpPayWay getDataById(String id) {
        return rpPayWayDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, RpPayWay rpPayWay) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        paramMap.put("payProductCode", rpPayWay.getPayProductCode());
        paramMap.put("payWayName", rpPayWay.getPayWayName());
        paramMap.put("payTypeName", rpPayWay.getPayTypeName());
        return rpPayWayDao.listPage(pageParam, paramMap);
    }

    @Override
    public RpPayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String payTypeCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("payTypeCode", payTypeCode);
        paramMap.put("payWayCode", payWayCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return rpPayWayDao.getBy(paramMap);
    }

    /**
     * 绑定支付费率
     * @param payWayCode
     * @param payTypeCode
     * @param payRate
     */
    @Override
    public void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate) throws PayBizException {
        RpPayWay payWay = getByPayWayTypeCode(payProductCode,payWayCode,payTypeCode);
        if(payWay!=null){
            throw new PayBizException(PayBizException.PAY_TYPE_IS_EXIST,"支付渠道已存在");
        }

        RpPayProduct rpPayProduct = rpPayProductService.getByProductCode(payProductCode, null);
        if(rpPayProduct.getAuditStatus().equals(PublicEnum.YES.name())){
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法绑定！");
        }

        RpPayWay rpPayWay = new RpPayWay();
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
    public List<RpPayWay> listByProductCode(String payProductCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("payProductCode", payProductCode);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return rpPayWayDao.listBy(paramMap);
    }

    /**
     * 获取所有支付方式
     */
    @Override
    public List<RpPayWay> listAll(){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return rpPayWayDao.listBy(paramMap);
    }
}