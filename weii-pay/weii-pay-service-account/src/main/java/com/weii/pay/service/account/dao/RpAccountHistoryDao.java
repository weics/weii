package com.weii.pay.service.account.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.account.vo.DailyCollectAccountHistoryVo;

import java.util.List;
import java.util.Map;

public interface RpAccountHistoryDao  extends BaseDao<RpAccountHistory> {
	List<RpAccountHistory> listPageByParams(Map<String, Object> params);
	
	List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(Map<String, Object> params);

	RpAccountHistory getByRequestNo(String requestNo);

	/** 更新账户风险预存期外的账户历史记录记为结算完成 **/
	void updateCompleteSettTo100(Map<String, Object> params);
}