package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.RpUserPayInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 0:26 2018/8/20
 * @Description:
 * @Modified By:
 */
@Repository
public interface RpUserPayInfoMapper {
    void insert(RpUserPayInfo rpUserPayInfo);

    void update(RpUserPayInfo rpUserPayInfo);

    RpUserPayInfo getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    RpUserPayInfo getByUserNo(String userNo, String payWayCode);
}
