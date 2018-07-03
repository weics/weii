package com.weii.admin.service.api;


import com.weii.admin.domain.User;
import com.weii.common.pojo.WeiiResult;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/06/24
 * @Description:
 * @Modified By:
 */
public interface UserService {



    WeiiResult authLogin(String username,String pwd);
}
