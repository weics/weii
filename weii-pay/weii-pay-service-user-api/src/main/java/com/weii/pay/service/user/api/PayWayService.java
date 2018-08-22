package com.weii.pay.service.user.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.PayWay;
import com.weii.pay.service.user.exceptions.PayBizException;

import java.util.List;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:27 2018/5/12
 * @Description: 支付方式service接口
 */
public interface PayWayService {
    /**
     * 保存
     */
    void saveData(PayWay payWay) throws PayBizException;

    /**
     * 更新
     */
    void updateData(PayWay payWay) throws PayBizException;

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    PayWay getDataById(String id) throws PayBizException;

    /**
     * 根据支付方式、渠道编码获取数据
     * @param rpTypeCode
     * @return
     */
    PayWay getByPayWayTypeCode(String payProductCode, String payWayCode, String rpTypeCode) throws PayBizException;


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, PayWay payWay) throws PayBizException;

    /**
     * 绑定支付费率
     * @param payWayCode
     * @param payTypeCode
     * @param payRate
     */
    void createPayWay(String payProductCode, String payWayCode, String payTypeCode, Double payRate)  throws PayBizException;

    /**
     * 根据支付产品获取支付方式
     * @param payProductCode
     */
    List<PayWay> listByProductCode(String payProductCode) throws PayBizException;

    /**
     * 获取所有支付方式
     */
    List<PayWay> listAll() throws PayBizException;
}
