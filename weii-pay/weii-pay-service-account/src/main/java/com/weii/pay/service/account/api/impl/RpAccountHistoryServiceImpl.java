package com.weii.pay.service.account.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.api.RpAccountHistoryService;
import com.weii.pay.service.account.dao.RpAccountHistoryDao;
import com.weii.pay.service.account.entity.RpAccountHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service("rpAccountHistoryService")
public class RpAccountHistoryServiceImpl implements RpAccountHistoryService {

	@Autowired
	private RpAccountHistoryDao rpAccountHistoryDao;
	
	@Override
	public void saveData(RpAccountHistory rpAccountHistory) {
		rpAccountHistoryDao.insert(rpAccountHistory);
	}

	@Override
	public void updateData(RpAccountHistory rpAccountHistory) {
		rpAccountHistoryDao.update(rpAccountHistory);
	}

	@Override
	public RpAccountHistory getDataById(String id) {
		return rpAccountHistoryDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpAccountHistory rpAccountHistory) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rpAccountHistoryDao.listPage(pageParam, paramMap);
	}
}