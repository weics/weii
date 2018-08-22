package com.weii.pay.service.user.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.entity.PayWay;
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
public interface PayWayMapper {
    void insert(PayWay payWay);

    void update(PayWay payWay);

    PayWay getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    PayWay getBy(Map<String,Object> paramMap);

    List<PayWay> listBy(Map<String,Object> paramMap);
}
