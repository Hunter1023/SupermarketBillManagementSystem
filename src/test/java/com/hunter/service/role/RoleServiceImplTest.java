package com.hunter.service.role;

import com.hunter.pojo.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RoleServiceImplTest {
    private RoleService roleService = new RoleServiceImpl();

    @Test
    public void should_get_role_list_when_get_role_list() {
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
