package com.weii.admin.service.api.iml;

import com.alibaba.fastjson.JSONObject;
import com.weii.admin.dao.mapper.PermissionMapper;
import com.weii.admin.service.api.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author: weics
 * @Date: Created in 17:15 2018/7/14
 * @Description:
 * @Modified By:
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;



    @Override
    public List<JSONObject> findAllResourcePermission() {
        return null;
    }

    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = permissionMapper.getUserPermission(username);
        //管理员角色ID为1
        int adminRoleId = 1;
        //如果是管理员
        String roleIdKey = "roleId";
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
            Set<String> menuList = permissionMapper.getAllMenu();
            Set<String> permissionList = permissionMapper.getAllPermission();
            userPermission.put("menuList", menuList);
            userPermission.put("permissionList", permissionList);
        }
        return userPermission;


        return null;
    }
}
