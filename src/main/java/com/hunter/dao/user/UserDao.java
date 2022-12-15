package com.hunter.dao.user;

import com.hunter.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    /**
     * 得到要登录的用户
     *
     * @param connection 与数据库的连接
     * @param userCode   userCode
     * @param password   password
     * @return 用户
     * @throws SQLException SQLException
     */
    User getLoginUser(Connection connection, String userCode, String password) throws SQLException;
}
