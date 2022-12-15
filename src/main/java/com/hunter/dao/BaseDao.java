package com.hunter.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 操作数据库的公共基类
 */
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块，类加载的时候初始化
    static {
        Properties properties = new Properties();

        // 通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * 获取数据库的连接
     *
     * @return 数据库的连接
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 查询数据库
     *
     * @param connection 数据库连接
     * @param sql        sql语句
     * @param params     sql语句的参数
     * @return 结果集
     * @throws SQLException SQLException
     */
    public static ResultSet executeQuery(Connection connection, String sql, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            // setObject，占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * 增删改数据库
     *
     * @param connection 数据库连接
     * @param sql        sql语句
     * @param params     sql语句的参数
     * @return 涉及的数据库行数
     * @throws SQLException SQLException
     */
    public static int executeUpdate(Connection connection, String sql, Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            // setObject，占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    /**
     * 关闭资源
     *
     * @param connection        连接
     * @param preparedStatement 预编译语句
     * @param resultSet         结果集
     * @return 是否关闭成功
     */
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean isClosed = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                // GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                isClosed = false;
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                // GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                isClosed = false;
            }
        }

        if (connection != null) {
            try {
                connection.close();
                // GC回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                isClosed = false;
            }
        }

        return isClosed;
    }
}
