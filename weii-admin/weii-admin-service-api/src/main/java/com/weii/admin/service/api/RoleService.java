package com.weii.admin.service.api;


import com.weii.domain.admin.entity.Role;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 21:50 2018/06/24
 * @Description:
 * @Modified By:
 */
public interface RoleService {


    int update(Role role);

    int list(Role role);

    void deleteById(Long id);

    List<String> findAllRoleWithPermission();

    void save(Role role);
}
