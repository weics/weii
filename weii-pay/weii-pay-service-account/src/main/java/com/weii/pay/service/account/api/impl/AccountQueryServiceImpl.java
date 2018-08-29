
package com.weii.pay.service.account.api.impl;


import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.DateUtils;
import com.weii.pay.service.account.api.AccountQueryService;

import com.weii.pay.service.account.dao.AccountHistoryMapper;
import com.weii.pay.service.account.dao.AccountMapper;
import com.weii.pay.service.account.dao.RpAccountDao;
import com.weii.pay.service.account.dao.RpAccountHistoryDao;
import com.weii.pay.service.account.entity.RpAccount;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.account.exceptions.AccountBizException;
import com.weii.pay.service.account.vo.DailyCollectAccountHistoryVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@com.alibaba.dubbo.config.annotation.Service(
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
@Service("accountQueryService")
public class AccountQueryServiceImpl implements AccountQueryService {
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountHistoryMapper accountHistoryMapper;

	private static final Logger LOG = LoggerFactory.getLogger(AccountQueryServiceImpl.class);

	/**
	 * 根据账户编号获取账户信息
	 * 
	 * @param accountNo
	 *            账户编号
	 * @return
	 */
	public RpAccount getAccountByAccountNo(String accountNo) {
		LOG.info("根据账户编号查询账户信息");
		RpAccount account = this.accountMapper.getByAccountNo(accountNo);
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setEditTime(new Date());
			accountMapper.update(account);
		}
		return account;
	}

	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNo
	 *            用户编号
	 * @return
	 */
	public RpAccount getAccountByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		LOG.info("根据用户编号查询账户信息");
		RpAccount account = this.accountMapper.getBy(map);
		if (account == null) {
			throw AccountBizException.ACCOUNT_NOT_EXIT;
		}
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setTodayExpend(BigDecimal.ZERO);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setEditTime(new Date());
			accountMapper.update(account);
		}
		return account;
	}

	/**
	 * 分页查询账户历史单用户
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, String accountNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		return accountMapper.listPage(pageParam, params);
	}

	/**
	 * 分页查询账户历史单角色
	 */
	public PageBean queryAccountHistoryListPageByRole(PageParam pageParam, Map<String, Object> params){
		String accountType = (String) params.get("accountType");
		if (StringUtils.isBlank(accountType)) {
			throw AccountBizException.ACCOUNT_TYPE_IS_NULL;
		}
		return accountMapper.listPage(pageParam, params);

	}

	/**
	 * 获取账户历史单角色
	 * 
	 * @param accountNo
	 *            账户编号
	 * @param requestNo
	 *            请求号
	 * @param trxType
	 *            业务类型
	 * @return AccountHistory
	 */
	public RpAccountHistory getAccountHistoryByAccountNo_requestNo_trxType(String accountNo, String requestNo, Integer trxType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNo", accountNo);
		map.put("requestNo", requestNo);
		map.put("trxType", trxType);
		return accountHistoryMapper.getBy(map);
	}

	/**
	 * 日汇总账户待结算金额 .
	 * 
	 * @param accountNo
	 *            账户编号
	 * @param statDate
	 *            统计日期
	 * @param riskDay
	 *            风险预测期
	 * @param fundDirection
	 *            资金流向
	 * @return
	 */
	public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(String accountNo, String statDate, Integer riskDay, Integer fundDirection) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accountNo", accountNo);
		params.put("statDate", statDate);
		params.put("riskDay", riskDay);
		params.put("fundDirection", fundDirection);
		return accountHistoryMapper.listDailyCollectAccountHistoryVo(params);

	}

	/**
	 * 根据参数分页查询账户.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            查询参数，可以为null.
	 * @return AccountList.
	 */
	public PageBean queryAccountListPage(PageParam pageParam, Map<String, Object> params) {

		return accountMapper.listPage(pageParam, params);
	}

	/**
	 * 根据参数分页查询账户历史.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            查询参数，可以为null.
	 * @return AccountHistoryList.
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, Map<String, Object> params) {

		return accountHistoryMapper.listPage(pageParam, params);
	}
	
    /**
	 * 获取所有账户
	 * @return
	 */
    @Override
    public List<RpAccount> listAll(){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return accountMapper.listBy(paramMap);
	}

}