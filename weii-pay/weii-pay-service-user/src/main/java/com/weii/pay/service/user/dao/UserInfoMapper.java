package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.UserInfo;
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
public interface UserInfoMapper {
    void insert(UserInfo userInfo);

    void update(UserInfo userInfo);

    UserInfo getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    String buildUserNo();

    String buildAccountNo();

    UserInfo getBy(Map<String,Object> paramMap);

    List<UserInfo> listBy(Map<String,Object> paramMap);
}
