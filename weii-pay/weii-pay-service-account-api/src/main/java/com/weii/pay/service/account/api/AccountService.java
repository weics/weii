package com.weii.pay.service.account.api;


import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.entity.RpAccount;
import com.weii.pay.service.account.exceptions.AccountBizException;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:55 2018/5/12
 * @Description:
 */
public interface AccountService {
	
	/**
	 * 保存
	 */
	void saveData(RpAccount rpAccount) throws AccountBizException;

	/**
	 * 更新
	 */
	void updateData(RpAccount rpAccount) throws AccountBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccount getDataById(String id) throws AccountBizException;
	

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpAccount rpAccount) throws AccountBizException;
	
}