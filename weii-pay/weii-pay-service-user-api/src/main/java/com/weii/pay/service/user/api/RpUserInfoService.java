package com.weii.pay.service.user.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.RpUserInfo;
import com.weii.pay.service.user.exceptions.PayBizException;

import java.util.List;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:28 2018/5/12
 * @Description: 用户信息service接口
 */
public interface RpUserInfoService {
    /**
     * 保存
     */
    void saveData(RpUserInfo rpUserInfo) throws PayBizException;

    /**
     * 更新
     */
    void updateData(RpUserInfo rpUserInfo) throws PayBizException;

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    RpUserInfo getDataById(String id) throws PayBizException;


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, RpUserInfo rpUserInfo) throws PayBizException;

    /**
     * 用户线下注册
     *
     * @param userName
     *            用户名
     * @return
     */
    void registerOffline(String userName)  throws PayBizException;

    /**
     * 根据商户编号获取商户信息
     * @param merchantNo
     * @return
     */
    RpUserInfo getDataByMerchentNo(String merchantNo) throws PayBizException;

    /**
     * 获取所有用户
     * @return
     */
    List<RpUserInfo> listAll() throws PayBizException;
}
