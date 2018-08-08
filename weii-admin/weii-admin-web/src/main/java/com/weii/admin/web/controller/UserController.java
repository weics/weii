package com.weii.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weii.admin.service.api.PermissionService;
import com.weii.admin.service.api.UserService;
import com.weii.admin.web.utils.constants.Constants;
import com.weii.common.enums.ErrorEnum;
import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.User;
import com.weii.domain.admin.vo.PermissionVo;
import com.weii.domain.admin.vo.UserVo;
import netscape.security.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: weics
 * @Date: Created in 21:10 2018/06/24
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/login")
    @ResponseBody
    public WeiiResult login(String username ,String password) {

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            return WeiiResult.ok();
        } catch (AuthenticationException e) {
            return WeiiResult.build(400,"用户或者密码错误");
        }

    }

    /**
     * 查询当前登录用户的信息
     *
     * @return
     */
    @RequestMapping("/getInfo")
    public WeiiResult getInfo() {
        Session session = SecurityUtils.getSubject().getSession();
        try {
            //从session获取用户信息
            JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
            String username = userInfo.getString("username");
            Map<String,Object> data = new HashMap<>(10);
            final List<PermissionVo> userPermission = permissionService.getUserPermission(username);
            session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);

            data.put("userPermission", userPermission);
            return WeiiResult.ok(data);
        } catch (Exception e) {

        }
        return  WeiiResult.build(ErrorEnum.E_400);

    }


    @RequestMapping("list")
    public WeiiResult userList() {
        Session session = SecurityUtils.getSubject().getSession();
        String username = "admin";

        PageHelper.startPage(1,10);


        PageInfo<UserVo> pageInfo = new PageInfo<>(userService.getUserList());


        return WeiiResult.ok(pageInfo);
    }


    @GetMapping("/logout")
    public WeiiResult logout(final Principal user) {
        return WeiiResult.ok();
    }


}
