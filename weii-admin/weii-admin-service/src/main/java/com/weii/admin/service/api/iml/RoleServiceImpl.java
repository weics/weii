package com.weii.admin.service.api.iml;

import com.weii.admin.dao.mapper.RoleMapper;
import com.weii.admin.service.api.RoleService;
import com.weii.domain.admin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 22:00 2018/06/28
 * @Description:
 * @Modified By:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void save(Role role) {

    }

    @Override
    public int update(Role role) {
        return 0;
    }

    @Override
    public int list(Role role) {
        return 0;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<String> findAllRoleWithPermission() {
        return null;
    }


}
