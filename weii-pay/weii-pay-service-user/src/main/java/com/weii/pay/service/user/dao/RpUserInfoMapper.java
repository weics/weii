package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.RpUserInfo;
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
public interface RpUserInfoMapper {
    void insert(RpUserInfo rpUserInfo);

    void update(RpUserInfo rpUserInfo);

    RpUserInfo getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    String buildUserNo();

    String buildAccountNo();

    RpUserInfo getBy(Map<String,Object> paramMap);

    List<RpUserInfo> listBy(Map<String,Object> paramMap);
}
