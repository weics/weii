package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.UserPayInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 0:26 2018/8/20
 * @Description:
 * @Modified By:
 */
@Repository
public interface UserPayInfoMapper {
    void insert(UserPayInfo userPayInfo);

    void update(UserPayInfo userPayInfo);

    UserPayInfo getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    UserPayInfo getByUserNo(String userNo, String payWayCode);
}
