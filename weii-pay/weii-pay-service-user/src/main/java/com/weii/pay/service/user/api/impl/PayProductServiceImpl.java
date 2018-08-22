package com.weii.pay.service.user.api.impl;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.PayProductService;
import com.weii.pay.service.user.entity.PayProduct;
import com.weii.pay.service.user.exceptions.PayBizException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:32 2018/5/12
 * @Description: 支付产品service实现类
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class PayProductServiceImpl implements PayProductService {
    @Override
    public void saveData(PayProduct payProduct) throws PayBizException {

    }

    @Override
    public void updateData(PayProduct payProduct) throws PayBizException {

    }

    @Override
    public PayProduct getDataById(String id) throws PayBizException {
        return null;
    }

    @Override
    public PageBean listPage(PageParam pageParam, PayProduct payProduct) throws PayBizException {
        return null;
    }

    @Override
    public PayProduct getByProductCode(String productCode, String auditStatus) throws PayBizException {
        return null;
    }

    @Override
    public void createPayProduct(String productCode, String productName) throws PayBizException {

    }

    @Override
    public void deletePayProduct(String productCode) throws PayBizException {

    }

    @Override
    public List<PayProduct> listAll() throws PayBizException {
        return null;
    }

    @Override
    public void audit(String productCode, String auditStatus) throws PayBizException {

    }
}
