package com.hunter.dao.user;

import com.hunter.dao.BaseDao;
import com.hunter.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        int affectRowsCnt = 0;

        if (connection != null) {
            String sql = "UPDATE smbms_user SET userPassword = ? where id = ?";
            Object[] params = {password, id};
            affectRowsCnt = BaseDao.executeUpdate(connection, sql, params);
        }
        return affectRowsCnt;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT count(*) AS count FROM smbms_user a INNER JOIN smbms_role b ON a.userRole = b.id");
            // 存放参数
            List<Object> list = addExtraQueryStr(userName, userRole, sql);

            Object[] params = list.toArray();
            rs = BaseDao.executeQuery(connection, String.valueOf(sql), params);

            if (rs.next()) {
                // 从结果集中获取数量
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, null, rs);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNum, int pageSize) throws SQLException {
        ResultSet rs = null;
        User user = null;
        List<User> userList = new ArrayList<>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT a.*, b.roleName AS userRoleName FROM smbms_user a INNER JOIN smbms_role b ON a.userRole = b.id");
            List<Object> list = addExtraQueryStr(userName, userRole, sql);

            sql.append(" ORDER BY creationDate DESC LIMIT ? OFFSET ?");
            list.add(pageSize);
            int currentStartNum = (currentPageNum - 1) * pageSize;
            list.add(currentStartNum);

            Object[] params = list.toArray();
            rs = BaseDao.executeQuery(connection, sql.toString(), params);
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getInt("userRole"));
                user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(user);
            }
            BaseDao.closeResource(null, null, rs);
        }
        return userList;
    }

    private List<Object> addExtraQueryStr(String userName, int userRole, StringBuffer sql) {
        List<Object> list = new ArrayList<>();

        boolean hasWhere = false;
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" WHERE a.userName LIKE ?");
            list.add("%" + userName + "%"); // 模糊查询
            hasWhere = true;
        }
        if (userRole > 0) {
            if (hasWhere) {
                sql.append(" AND");
            } else {
                sql.append(" WHERE");
            }
            sql.append(" a.userRole = ?");
            list.add(userRole);
        }
        return list;
    }

    @Override
    public int addUser(Connection connection, User user) throws SQLException {
        int addUserCnt = 0;

        if (connection != null) {
            String sql = "INSERT INTO smbms_user(id, userCode, userName, userPassword, " +
                    "gender, birthday, phone, address, userRole) VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(),
                    user.getGender(), user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole()};
            addUserCnt = BaseDao.executeUpdate(connection, sql, params);
        }
        return addUserCnt;
    }

    @Override
    public long getUserId(Connection connection, String userCode) throws SQLException {
        ResultSet rs = null;
        long userId = 0;

        if (connection != null) {
            String sql = "SELECT id FROM smbms_user WHERE userCode = ?";
            Object[] params = {userCode};

            rs = BaseDao.executeQuery(connection, String.valueOf(sql), params);

            if (rs.next()) {
                // 从结果集中获取用户id
                userId = rs.getLong("id");
            }
            BaseDao.closeResource(null, null, rs);
        }
        return userId;
    }

    @Override
    public User getUser(Connection connection, String userCode) throws SQLException {
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "SELECT a.*, b.roleName AS userRoleName FROM smbms_user a INNER JOIN smbms_role b ON a.userRole = b.id WHERE userCode = ?";
            Object[] params = {userCode};

            rs = BaseDao.executeQuery(connection, String.valueOf(sql), params);

            if (rs.next()) {
                user = new User();
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRoleName(rs.getString("userRoleName"));
            }
            BaseDao.closeResource(null, null, rs);
        }
        return user;
    }
}
