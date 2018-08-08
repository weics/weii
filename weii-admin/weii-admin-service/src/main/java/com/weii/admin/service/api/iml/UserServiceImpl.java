package com.weii.admin.service.api.iml;

import com.weii.admin.dao.mapper.UserMapper;
import com.weii.admin.service.api.UserService;
import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.User;
import com.weii.domain.admin.entity.UserExample;
import com.weii.domain.admin.vo.UserVo;
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

    @Override
    public WeiiResult getInfo() {
        return WeiiResult.ok();
    }

    @Override
    public List<UserVo> getUserList() {

        // startPage(第几页, 多少条数据)

//        UserExample example = new UserExample();
//        final List<User> users = userMapper.selectByExample(example);

        final List<UserVo> allUser = userMapper.getAllUser();

        return allUser;
    }
}
