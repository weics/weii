package com.weii.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.weii.admin.service.api.PermissionService;
import com.weii.admin.web.utils.constants.Constants;
import com.weii.common.enums.ErrorEnum;
import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.vo.PermissionVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/8/2
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/auth")
    @ResponseBody
    public WeiiResult login(@RequestBody JSONObject requestJson) {
        final String username = requestJson.getString("username");
        final String password = requestJson.getString("password");
        System.out.println("输入的用户名和密码:username:"+username+"----pwd:"+password);
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
//            JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
//            String username = userInfo.getString("username");
            Map<String,Object> data = new HashMap<>(10);
//            final List<PermissionVo> userPermission = permissionService.getUserPermission(username);
//            session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
            /**
             * "menuList":[
             *             "role",
             *             "user",
             *             "article"
             *          ],
             *          "roleId":1,
             *          "nickname":"超级用户",
             *          "roleName":"管理员",
             *          "permissionList":[
             *             "article:list",
             *             "article:add",
             *             "user:list",
             *          ],
             *          "userId":10003
             */


            Map<String,Object> map = new HashMap<>();
            List<String> menuList = new ArrayList<>();
            menuList.add("role");
            menuList.add("user");
            menuList.add("article");
            map.put("menuList",menuList);
            map.put("roleId",1);
            map.put("nickname","超级用户");
            map.put("roleName","管理员");
            List<String> permissionList = new ArrayList<>();
            permissionList.add("article:list");
            permissionList.add("article:add");
            permissionList.add("user:list");
            map.put("permissionList",permissionList);
            map.put("userId",10003);


            data.put("userPermission", map);
            return WeiiResult.ok(data);
        } catch (Exception e) {

        }
        return  WeiiResult.build(ErrorEnum.E_400);

    }

}
