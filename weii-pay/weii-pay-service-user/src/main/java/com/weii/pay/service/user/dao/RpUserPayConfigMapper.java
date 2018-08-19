package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.RpUserPayConfig;
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
public interface RpUserPayConfigMapper {
    RpUserPayConfig getBy(Map<String,Object> paramMap);

    RpUserPayConfig getByUserNo(String userNo, Object o);

    List<RpUserPayConfig> listBy(Map<String,Object> paramMap);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    RpUserPayConfig getById(String id);

    void update(RpUserPayConfig rpUserPayConfig);

    void insert(RpUserPayConfig rpUserPayConfig);
}
