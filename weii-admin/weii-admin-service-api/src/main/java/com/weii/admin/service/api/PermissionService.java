package com.weii.admin.service.api;

import com.alibaba.fastjson.JSONObject;
import com.weii.domain.admin.vo.PermissionVo;

import java.util.List;

/**
 * @Author: weics
 * @Date: Created in 20:51 2018/06/24
 * @Description:
 * @Modified By:
 */
public interface PermissionService {

    List<JSONObject> findAllResourcePermission();

    List<PermissionVo> getUserPermission(String username);
}
