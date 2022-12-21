package com.hunter.dao.user;

import com.hunter.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * 修改用户密码
     *
     * @param connection 与数据库的连接
     * @param id         id 用户id
     * @param password   password
     * @return 影响的用户数
     * @throws SQLException SQLException
     */
    int updatePwd(Connection connection, int id, String password) throws SQLException;

    /**
     * 根据用户名或角色，查询用户总数
     *
     * @param connection 与数据库的连接
     * @param userName   用户名
     * @param userRole   用户角色
     * @return 用户总数
     * @throws SQLException SQLException
     */
    int getUserCount(Connection connection, String userName, int userRole) throws SQLException;

    /**
     * 通过条件 查询用户列表
     *
     * @param connection     与数据库的连接
     * @param userName       用户名
     * @param userRole       用户角色
     * @param currentPageNum 当前页数
     * @param pageSize       页面大小
     * @return 用户列表
     * @throws SQLException SQLException
     */
    List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNum, int pageSize) throws SQLException;

    /**
     * 添加用户
     *
     * @param connection 与数据库的连接
     * @param user       用户
     * @return 添加的用户数
     * @throws SQLException SQLException
     */
    int addUser(Connection connection, User user) throws SQLException;

    /**
     * 根据用户编码，获取用户id
     *
     * @param connection 与数据库的连接
     * @param userCode   用户编码
     * @return 用户id
     * @throws SQLException SQLException
     */
    long getUserId(Connection connection, String userCode) throws SQLException;

    /**
     * 根据用户编码，获取用户
     *
     * @param connection 与数据库的连接
     * @param id 用户id
     * @return 用户
     * @throws SQLException SQLException
     */
    User getUser(Connection connection, Integer id) throws SQLException;

    /**
     * 修改用户信息
     *
     * @param connection 与数据库的连接
     * @param user 用户
     * @return 影响的用户数
     * @throws SQLException SQLException
     */
    int updateUser(Connection connection, User user) throws SQLException;
}
