package com.weii.pay.service.account.api;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.entity.RpAccount;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.account.exceptions.AccountBizException;
import com.weii.pay.service.account.vo.DailyCollectAccountHistoryVo;

import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 10:55 2018/5/12
 * @Description:
 */
public interface RpAccountQueryService {
    /**
     * 根据账户编号获取账户信息
     *
     * @param accountNo
     *            账户编号
     * @return
     */
    RpAccount getAccountByAccountNo(String accountNo) throws AccountBizException;

    /**
     * 根据用户编号编号获取账户信息
     *
     * @param userNo
     *            用户编号
     * @return
     */
    RpAccount getAccountByUserNo(String userNo) throws AccountBizException;

    // /////////////////////账户历史/////////////////////////////

    /**
     * 根据账户编号分页查询账户历史单商户.
     *
     * @param pageParam
     *            分页参数.
     * @param accountNo
     *            账户编号.
     * @return AccountHistoryList.
     * @throws AccountBizException
     */
    PageBean queryAccountHistoryListPage(PageParam pageParam, String accountNo) throws AccountBizException;

    /**
     * 根据账户编号分页查询账户历史单角色.
     *
     * @param pageParam
     *            分页参数.
     * @param params
     *            参数
     * @return AccountHistoryList.
     * @throws AccountBizException
     */
    PageBean queryAccountHistoryListPageByRole(PageParam pageParam, Map<String, Object> params) throws AccountBizException;


    /**
     * 获取账户历史（参数没有可以传null）
     *
     * @param accountNo
     *            账户编号
     * @param requestNo
     *            请求号
     * @param trxType
     *            业务类型
     * @return AccountHistory
     */
    RpAccountHistory getAccountHistoryByAccountNo_requestNo_trxType(String accountNo, String requestNo, Integer trxType) throws AccountBizException;

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
     * @throws AccountBizException
     */
    List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(String accountNo, String statDate, Integer riskDay, Integer fundDirection) throws AccountBizException;

    /**
     * 根据参数分页查询账户.
     *
     * @param pageParam
     *            分页参数.
     * @param params
     *            查询参数，可以为null.
     * @return AccountList.
     * @throws AccountBizException
     */
    PageBean queryAccountListPage(PageParam pageParam, Map<String, Object> params) throws AccountBizException;


    /**
     * 根据参数分页查询账户历史.
     *
     * @param pageParam
     *            分页参数.
     * @param params
     *            查询参数，可以为null.
     * @return AccountHistoryList.
     * @throws AccountBizException
     */
    PageBean queryAccountHistoryListPage(PageParam pageParam, Map<String, Object> params) throws AccountBizException;

    /**
     * 获取所有账户
     * @return
     */
    List<RpAccount> listAll() throws AccountBizException;
}
