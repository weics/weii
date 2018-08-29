package com.weii.pay.service.account.api.impl;


import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.api.AccountService;
import com.weii.pay.service.account.dao.AccountMapper;
import com.weii.pay.service.account.dao.RpAccountDao;
import com.weii.pay.service.account.entity.RpAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@com.alibaba.dubbo.config.annotation.Service(
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	public void saveData(RpAccount rpAccount) {
		accountMapper.insert(rpAccount);
	}

	@Override
	public void updateData(RpAccount rpAccount) {
		accountMapper.update(rpAccount);
	}

	@Override
	public RpAccount getDataById(String id) {
		return accountMapper.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpAccount rpAccount) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", rpAccount.getAccountNo());
		return accountMapper.listPage(pageParam, paramMap);
	}
}