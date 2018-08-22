package com.weii.pay.service.user.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.PayProduct;
import com.weii.pay.service.user.exceptions.PayBizException;

import java.util.List;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:39 2018/5/11
 * @Description:
 */
public interface PayProductService {
    /**
     * 保存
     */
    void saveData(PayProduct payProduct) throws PayBizException;

    /**
     * 更新
     */
    void updateData(PayProduct payProduct) throws PayBizException;

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    PayProduct getDataById(String id) throws PayBizException;


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, PayProduct payProduct) throws PayBizException;

    /**
     * 根据产品编号获取支付产品
     * @param productCode
     * @return
     */
    PayProduct getByProductCode(String productCode, String auditStatus) throws PayBizException;

    /**
     * 创建支付产品
     * @param productCode
     * @param productName
     */
    void createPayProduct(String productCode, String productName) throws PayBizException;

    /**
     * 删除支付产品
     * @param productCode
     */
    void deletePayProduct(String productCode) throws PayBizException;

    /**
     * 获取所有支付产品
     */
    List<PayProduct> listAll() throws PayBizException;

    /**
     * 审核
     * @param productCode
     * @param auditStatus
     */
    void audit(String productCode, String auditStatus) throws PayBizException;
}
