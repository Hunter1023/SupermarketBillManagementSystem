package com.hunter.service.role;

import com.hunter.pojo.Role;

import java.util.List;

public interface RoleService {
    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    List<Role> getRoleList();
}
