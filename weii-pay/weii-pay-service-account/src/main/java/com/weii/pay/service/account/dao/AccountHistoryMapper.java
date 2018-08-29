package com.weii.pay.service.account.dao;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.account.vo.DailyCollectAccountHistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 23:15 2018/8/29
 * @Description:
 * @Modified By:
 */
@Mapper
public interface AccountHistoryMapper {
    public void insert(RpAccountHistory rpAccountHistory);

    public void update(RpAccountHistory rpAccountHistory);

    RpAccountHistory getById(String id);

    PageBean listPage(PageParam pageParam, Map<String,Object> paramMap);

    RpAccountHistory getBy(Map<String,Object> map);

    List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(Map<String,Object> params);

    void updateCompleteSettTo100(Map<String,Object> params);
}
