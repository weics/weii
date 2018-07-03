package com.weii.admin.service.api.iml;

import com.weii.admin.dao.entity.User;
import com.weii.admin.dao.entity.UserExample;
import com.weii.admin.dao.mapper.UserMapper;
import com.weii.admin.service.api.UserService;
import com.weii.common.pojo.WeiiResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:27 2018/07/03
 * @Description:
 * @Modified By:
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public WeiiResult authLogin(String username,String pwd) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        criteria.andPasswordEqualTo(pwd);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0){
            return WeiiResult.ok();
        }
        return WeiiResult.build(400,"用户名或者密码错误");
    }
}
