package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.RpPayWay;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 0:25 2018/8/20
 * @Description:
 * @Modified By:
 */
@Repository
public interface RpPayWayMapper {
    void insert(RpPayWay rpPayWay);

    void update(RpPayWay rpPayWay);

    RpPayWay getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    RpPayWay getBy(Map<String,Object> paramMap);

    List<RpPayWay> listBy(Map<String,Object> paramMap);
}
