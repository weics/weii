package com.weii.pay.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.account.api.AccountHistoryService;
import com.weii.pay.service.account.api.AccountService;
import com.weii.pay.service.account.entity.RpAccount;
import com.weii.pay.service.account.entity.RpAccountHistory;
import com.weii.pay.service.user.api.UserInfoService;
import com.weii.pay.service.user.entity.UserInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 22:06 2018/5/12
 * @Description:
 * @Modified By:
 */
public class AccountController {



    @Reference(version = "1.0.0")
    private AccountService accountService;
    @Reference(version = "1.0.0")
    private UserInfoService userInfoService;
    @Reference(version = "1.0.0")
    private AccountHistoryService accountHistoryService;

    /**
     * 函数功能说明 ： 查询账户信息
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(RpAccount rpAccount, PageParam pageParam, Model model) {
        PageBean pageBean = accountService.listPage(pageParam, rpAccount);
        List<Object> recordList = pageBean.getRecordList();
        for(Object obj : recordList){
            RpAccount account = (RpAccount)obj;
            UserInfo userInfo = userInfoService.getDataByMerchentNo(account.getUserNo());
            account.setUserName(userInfo.getUserName());
        }
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpAccount",rpAccount);
        return "account/list";
    }

    /**
     * 函数功能说明 ： 查询账户历史信息
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/historyList", method ={RequestMethod.POST,RequestMethod.GET})
    public String historyList(RpAccountHistory rpAccountHistory, PageParam pageParam, Model model) {
        PageBean pageBean = accountHistoryService.listPage(pageParam, rpAccountHistory);
        List<Object> recordList = pageBean.getRecordList();
        for(Object obj : recordList){
            RpAccountHistory history = (RpAccountHistory)obj;
            UserInfo userInfo = userInfoService.getDataByMerchentNo(history.getUserNo());
            history.setUserName(userInfo.getUserName());
        }
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpAccountHistory",rpAccountHistory);
        return "account/historyList";
    }
}
