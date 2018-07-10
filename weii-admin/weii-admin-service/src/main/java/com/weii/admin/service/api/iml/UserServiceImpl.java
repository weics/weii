package com.weii.admin.service.api.iml;

import com.weii.admin.dao.mapper.UserMapper;
import com.weii.admin.service.api.UserService;
import com.weii.domain.admin.entity.User;
import com.weii.domain.admin.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:27 2018/07/03
 * @Description:
 * @Modified By:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User authLogin(String username,String pwd) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        criteria.andPasswordEqualTo(pwd);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0){
            return users.get(0);
        }
        return null;
    }
}
