package com.weii.pay.service.user.api.impl;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.RpUserPayInfoService;
import com.weii.pay.service.user.dao.RpUserPayInfoMapper;
import com.weii.pay.service.user.entity.RpUserPayInfo;
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
public class RpUserPayInfoServiceImpl implements RpUserPayInfoService {

    @Autowired
    private RpUserPayInfoMapper rpUserPayInfoMapper;

    @Override
    public void saveData(RpUserPayInfo rpUserPayInfo) {
        rpUserPayInfoMapper.insert(rpUserPayInfo);
    }

    @Override
    public void updateData(RpUserPayInfo rpUserPayInfo) {
        rpUserPayInfoMapper.update(rpUserPayInfo);
    }

    @Override
    public RpUserPayInfo getDataById(String id) {
        return rpUserPayInfoMapper.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, RpUserPayInfo rpUserPayInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        return rpUserPayInfoMapper.listPage(pageParam, paramMap);
    }

    /**
     * 通过商户编号获取商户支付配置信息
     *
     * @param userNo
     * @return
     */
    @Override
    public RpUserPayInfo getByUserNo(String userNo, String payWayCode) {
        return rpUserPayInfoMapper.getByUserNo(userNo, payWayCode);
    }
}
