package com.weii.pay.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.UserInfoService;
import com.weii.pay.service.user.entity.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 22:18 2018/5/12
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {
//    @Reference(version = "1.0.0")
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private UserInfoService userInfoService;

    /**
     * 函数功能说明 ： 查询用户信息
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public Object list(UserInfo userInfo, PageParam pageParam) {
        PageBean pageBean = userInfoService.listPage(pageParam, userInfo);
        Map<String,Object> result = new HashMap<>(3);
        result.put("pageBean", pageBean);
        result.put("pageParam", pageParam);
        result.put("rpUserInfo", userInfo);
        return result;
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
    public Object add(@RequestParam("userName") String userName) {
        userInfoService.registerOffline(userName);
        return "success";
    }

    /**
     * 函数功能说明 ： 查询用户信息 查找带回
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/lookupList", method ={RequestMethod.POST,RequestMethod.GET})
    public Object lookupList(UserInfo userInfo, PageParam pageParam) {
        PageBean pageBean = userInfoService.listPage(pageParam, userInfo);

        Map<String,Object> result = new HashMap<>(3);
        result.put("pageBean", pageBean);
        result.put("pageParam", pageParam);
        result.put("rpUserInfo", userInfo);
        return result;
    }
}
