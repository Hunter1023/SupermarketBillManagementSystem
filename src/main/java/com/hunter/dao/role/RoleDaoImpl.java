package com.hunter.dao.role;

import com.hunter.dao.BaseDao;
import com.hunter.pojo.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> getRoleList(Connection connection) throws SQLException {
        ResultSet rs = null;
        Role role = null;
        ArrayList<Role> roleArrayList = new ArrayList<>();

        if (connection != null) {
            String sql = "SELECT * FROM smbms_role";
            Object[] params = {};

            rs = BaseDao.executeQuery(connection, sql, params);
            while (rs.next()) {
                role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                roleArrayList.add(role);
            }
            BaseDao.closeResource(null, null, rs);
        }
        return roleArrayList;
    }
}