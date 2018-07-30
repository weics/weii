package com.weii.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.weii.admin.service.api.PermissionService;
import com.weii.admin.service.api.UserService;
import com.weii.admin.web.utils.constants.Constants;
import com.weii.common.enums.ErrorEnum;
import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.User;
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

//    @Resource
//    private JwtUtil jwtUtil;

//    @PostMapping
//    public WeiiResult register(@RequestBody @Valid final User user,
//                               final BindingWeiiResult bindingWeiiResult) {
//        // {"username":"123456", "password":"123456", "email": "123456@qq.com"}
//        if (bindingWeiiResult.hasErrors()) {
//            //noinspection ConstantConditions
//            final String msg = bindingWeiiResult.getFieldError().getDefaultMessage();
//            return WeiiResult.ok(msg);
//        } else {
//            this.userService.save(user);
//            return this.getToken(user);
//        }
//    }
//
//
//    @DeleteMapping("/{id}")
//    public WeiiResult delete(@PathVariable final Long id) {
//        this.userService.deleteById(id);
//        return WeiiResult.ok();
//    }
//
//    @PostMapping("/password")
//    public WeiiResult validatePassword(@RequestBody final User user) {
//        final User oldUser = this.userService.findById(user.getId());
//        final boolean isValidate = this.userService.verifyPassword(user.getPassword(), oldUser.getPassword());
//        return WeiiResult.ok(isValidate);
//    }
//
//    @PutMapping
//    public WeiiResult update(@RequestBody final User user) {
//        this.userService.update(user);
//        return this.getToken(this.userService.findById(user.getId()));
//    }
//
//    @GetMapping("/{id}")
//    public WeiiResult detail(@PathVariable final Long id) {
//        final User user = this.userService.findById(id);
//        return WeiiResult.ok(user);
//    }
//
//    @GetMapping("/info")
//    public WeiiResult info(final Principal user) {
//        final User userDB = this.userService.findDetailByUsername(user.getName());
//        return WeiiResult.ok(userDB);
//    }
//
//
//    @GetMapping
//    public WeiiResult list(@RequestParam(defaultValue = "0") final Integer page,
//                       @RequestParam(defaultValue = "0") final Integer size) {
//        PageHelper.startPage(page, size);
//        final List<User> list = this.userService.findAllUserWithRole();
//        //noinspection unchecked
//        final PageInfo pageInfo = new PageInfo(list);
//        return WeiiResult.ok(pageInfo);
//    }

    @RequestMapping("/login")
    @ResponseBody
    public WeiiResult login(String username ,String password) {
//


        // {"username":"admin", "password":"admin123"}
        // {"email":"admin@qq.com", "password":"admin123"}
//        if (user.getUserName() == null) {
//            return WeiiResult.ok("username or email empty");
//        }
//        if (user.getPassword() == null) {
//            return WeiiResult.ok("password empty");
//        }

////        return WeiiResult.ok("123123123");
//        String username = user.getUserName();
//        String password = user.getPassword();

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            return WeiiResult.ok();
        } catch (AuthenticationException e) {
            return WeiiResult.build(400,"用户或者密码错误");
        }

//        final User user = userService.authLogin(username, password);
//        return WeiiResult.ok(user);


    }


    /**
     * 查询当前登录用户的信息
     *
     * @return
     */
    @PostMapping("/getInfo")
    public WeiiResult getInfo() {
        Session session = SecurityUtils.getSubject().getSession();
        try {
            //从session获取用户信息
            JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
            String username = userInfo.getString("username");
            JSONObject data = new JSONObject();
            JSONObject userPermission = permissionService.getUserPermission(username);
            session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);


            data.put("userPermission", userPermission);
            return WeiiResult.ok(data);
        } catch (Exception e) {
//            logger.error(e.getMessage());

        }
        return  WeiiResult.build(ErrorEnum.E_400);

    }


    @GetMapping("/logout")
    public WeiiResult logout(final Principal user) {
        return WeiiResult.ok();
    }


}
