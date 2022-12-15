package com.hunter.dao.user;

import com.hunter.dao.BaseDao;
import com.hunter.pojo.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode, String password) throws SQLException {
        User user = null;
        ResultSet rs = null;

        if (connection != null) {
            String sql = "SELECT * FROM smbms_user WHERE userCode = ? and userPassword = ?";
            Object[] params = {userCode, password};

            rs = BaseDao.executeQuery(connection, sql, params);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null, null, rs);
        }
        return user;
    }
}
