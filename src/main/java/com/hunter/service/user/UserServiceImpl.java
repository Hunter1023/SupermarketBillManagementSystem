package com.hunter.service.user;

import com.hunter.dao.BaseDao;
import com.hunter.dao.user.UserDao;
import com.hunter.dao.user.UserDaoImpl;
import com.hunter.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    // 业务层都得调用dao层，所以要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            // 通过业务层调用对应的具体数据库操作
            user = userDao.getLoginUser(connection, userCode, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    @Override
    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean isSucceed = false;
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection, id, password) > 0) {
                isSucceed = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return isSucceed;
    }

    @Override
    public int getUserCount(String userName, int userRole) {
        Connection connection = null;
        int userCount = 0;
        try {
            connection = BaseDao.getConnection();
            userCount = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userCount;
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNum, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, userName, userRole, currentPageNum, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }

    @Override
    public boolean addUser(User user) {
        Connection connection = null;
        boolean isSucceed = false;
        try {
            connection = BaseDao.getConnection();
            // 开启事务
            connection.setAutoCommit(false);
            int updateRows = userDao.addUser(connection, user);
            connection.commit();
            if (updateRows > 0) {
                isSucceed = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return isSucceed;
    }

    @Override
    public boolean isUserCodeExist(String userCode) {
        Connection connection = null;
        boolean isUserCodeExist = false;

        long userId = 0;
        try {
            connection = BaseDao.getConnection();
            userId = userDao.getUserId(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        if (userId > 0) {
            isUserCodeExist = true;
        }
        return isUserCodeExist;
    }

    @Override
    public User getUser(String userCode) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getUser(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }
}
