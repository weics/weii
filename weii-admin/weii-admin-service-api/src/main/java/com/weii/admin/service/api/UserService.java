package com.weii.admin.service.api;


import com.weii.common.pojo.WeiiResult;
import com.weii.domain.admin.entity.User;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/06/24
 * @Description:
 * @Modified By:
 */
public interface UserService {



    User authLogin(String username, String pwd);

    WeiiResult getInfo();

    List<User> getUserList();
}
