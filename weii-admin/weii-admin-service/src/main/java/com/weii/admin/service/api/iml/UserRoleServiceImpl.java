package com.weii.admin.service.api.iml;

import com.weii.admin.dao.mapper.UserRoleMapper;
import com.weii.admin.service.api.UserRoleService;
import com.weii.domain.admin.entity.User;
import com.weii.domain.admin.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 17:16 2018/7/14
 * @Description:
 * @Modified By:
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public void updateUserRole(User user) {

    }

    @Override
    public List<RoleVo> getAllRoles() {
        final List<RoleVo> allRoles = userRoleMapper.getAllRoles();
        return allRoles;
    }
}
