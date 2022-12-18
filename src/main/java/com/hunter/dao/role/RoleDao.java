package com.hunter.dao.role;

import com.hunter.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    /**
     * 获取角色列表
     *
     * @param connection 与数据库的连接
     * @return 角色列表
     * @throws SQLException SQLException
     */
    List<Role> getRoleList(Connection connection) throws SQLException;
}
