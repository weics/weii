package com.weii.pay.service.account.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.api.AccountHistoryService;
import com.weii.pay.service.account.dao.AccountHistoryMapper;
import com.weii.pay.service.account.dao.RpAccountHistoryDao;
import com.weii.pay.service.account.entity.RpAccountHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@com.alibaba.dubbo.config.annotation.Service(
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
@Service("accountHistoryService")
public class AccountHistoryServiceImpl implements AccountHistoryService {

	@Autowired
	private AccountHistoryMapper accountHistoryMapper;
	
	@Override
	public void saveData(RpAccountHistory rpAccountHistory) {
		accountHistoryMapper.insert(rpAccountHistory);
	}

	@Override
	public void updateData(RpAccountHistory rpAccountHistory) {
		accountHistoryMapper.update(rpAccountHistory);
	}

	@Override
	public RpAccountHistory getDataById(String id) {
		return accountHistoryMapper.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpAccountHistory rpAccountHistory) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return accountHistoryMapper.listPage(pageParam, paramMap);
	}
}