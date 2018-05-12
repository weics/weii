package com.weii.pay.controller.user;

import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.RpUserInfoService;
import com.weii.pay.service.user.entity.RpUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: weics
 * @Date: Created in 22:18 2018/5/12
 * @Description:
 * @Modified By:
 */
public class UserInfoController {
    @Autowired
    private RpUserInfoService rpUserInfoService;

    /**
     * 函数功能说明 ： 查询用户信息
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(RpUserInfo rpUserInfo,PageParam pageParam, Model model) {
        PageBean pageBean = rpUserInfoService.listPage(pageParam, rpUserInfo);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserInfo",rpUserInfo);
        return "user/info/list";
    }

    /**
     * 函数功能说明 ：跳转添加
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI() {

        return "user/info/add";
    }

    /**
     * 函数功能说明 ： 保存
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam("userName") String userName) {
        rpUserInfoService.registerOffline(userName);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

    /**
     * 函数功能说明 ： 查询用户信息 查找带回
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/lookupList", method ={RequestMethod.POST,RequestMethod.GET})
    public String lookupList(RpUserInfo rpUserInfo, PageParam pageParam, Model model) {
        PageBean pageBean = rpUserInfoService.listPage(pageParam, rpUserInfo);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserInfo",rpUserInfo);
        return "user/info/lookupList";
    }
}
