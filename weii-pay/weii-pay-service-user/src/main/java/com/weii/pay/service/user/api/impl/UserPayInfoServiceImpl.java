package com.weii.pay.service.user.api.impl;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.UserPayInfoService;
import com.weii.pay.service.user.dao.UserPayInfoMapper;
import com.weii.pay.service.user.entity.UserPayInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:51 2018/5/12
 * @Description:
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class UserPayInfoServiceImpl implements UserPayInfoService {

    @Autowired
    private UserPayInfoMapper userPayInfoMapper;

    @Override
    public void saveData(UserPayInfo userPayInfo) {
        userPayInfoMapper.insert(userPayInfo);
    }

    @Override
    public void updateData(UserPayInfo userPayInfo) {
        userPayInfoMapper.update(userPayInfo);
    }

    @Override
    public UserPayInfo getDataById(String id) {
        return userPayInfoMapper.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, UserPayInfo userPayInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return userPayInfoMapper.listPage(pageParam, paramMap);
    }

    /**
     * 通过商户编号获取商户支付配置信息
     *
     * @param userNo
     * @return
     */
    @Override
    public UserPayInfo getByUserNo(String userNo, String payWayCode) {
        return userPayInfoMapper.getByUserNo(userNo, payWayCode);
    }
}
