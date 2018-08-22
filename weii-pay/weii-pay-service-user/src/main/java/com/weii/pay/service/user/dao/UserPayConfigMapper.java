package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.UserPayConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 0:26 2018/8/20
 * @Description:
 * @Modified By:
 */
@Repository
public interface UserPayConfigMapper {
    UserPayConfig getBy(Map<String,Object> paramMap);

    UserPayConfig getByUserNo(String userNo, Object o);

    List<UserPayConfig> listBy(Map<String,Object> paramMap);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    UserPayConfig getById(String id);

    void update(UserPayConfig userPayConfig);

    void insert(UserPayConfig userPayConfig);
}
