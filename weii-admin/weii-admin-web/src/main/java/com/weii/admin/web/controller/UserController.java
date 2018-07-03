package com.weii.admin.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weii.admin.domain.User;
import com.weii.admin.service.api.UserService;
import com.weii.admin.web.jwt.JwtUtil;
import com.weii.common.pojo.WeiiResult;
import netscape.security.Principal;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;


/**
 * @Author: weics
 * @Date: Created in 21:10 2018/06/24
 * @Description:
 * @Modified By:
 */
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

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

    @PostMapping("/login")
    public WeiiResult login(@RequestBody final User user) {
        // {"username":"admin", "password":"admin123"}
        // {"email":"admin@qq.com", "password":"admin123"}
        if (user.getUsername() == null && user.getEmail() == null) {
            return WeiiResult.ok("username or email empty");
        }
        if (user.getPassword() == null) {
            return WeiiResult.ok("password empty");
        }
        // 用户名登录
//        User dbUser = null;
//        if (user.getUsername() != null) {
//            dbUser = this.userService.findBy("username", user.getUsername());
//            if (dbUser == null) {
//                return WeiiResult.ok("username error");
//            }
//        }
//        // 邮箱登录
//        if (user.getEmail() != null) {
//            dbUser = this.userService.findBy("email", user.getEmail());
//            if (dbUser == null) {
//                return WeiiResult.ok("email error");
//            }
//            user.setUsername(dbUser.getUsername());
//        }
        // 验证密码
        //noinspection ConstantConditions
//        if (!this.userService.verifyPassword(user.getPassword(), dbUser.getPassword())) {
//            return WeiiResult.ok("password error");
//        }
        // 更新登录时间
//        this.userService.updateLoginTimeByUsername(user.getUsername());
//        return this.getToken(user);
        /**
         * 登录
         *
         * @param requestJson
         * @return
         */

//            CommonUtil.hasAllRequired(requestJson, "username,password");
            return userService.authLogin("cheng","123456");



    }

    @GetMapping("/logout")
    public WeiiResult logout(final Principal user) {
        return WeiiResult.ok();
    }


}
