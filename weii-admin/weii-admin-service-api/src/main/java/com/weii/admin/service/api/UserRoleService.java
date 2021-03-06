package com.weii.admin.service.api;

import com.weii.domain.admin.entity.User;
import com.weii.domain.admin.vo.RoleVo;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/06/24
 * @Description:
 * @Modified By:
 */
public interface UserRoleService {
    void updateUserRole(User user);

    List<RoleVo> getAllRoles();
}
