package com.weii.pay.service.account.dao;


import com.weii.pay.common.core.dao.BaseDao;
import com.weii.pay.service.account.entity.RpAccount;

import java.util.Map;

public interface RpAccountDao  extends BaseDao<RpAccount> {
	RpAccount getByAccountNo(String accountNo);

	RpAccount getByUserNo(Map<String, Object> map);
}