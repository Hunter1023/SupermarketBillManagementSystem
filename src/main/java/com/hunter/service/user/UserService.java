package com.hunter.service.user;

import com.hunter.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 用户登录
     *
     * @param userCode userCode
     * @param password password
     * @return User
     */
    User login(String userCode, String password);

    /**
     * 根据用户ID修改密码
     *
     * @param id id
     * @param password password
     * @return 是否修改成功
     */
    boolean updatePwd(int id, String password);

    /**
     * 根据用户名或角色，查询用户总数
     *
     * @param userName 用户名
     * @param userRole 角色
     * @return 用户总数
     */
    int getUserCount(String userName, int userRole);

    /**
     * 通过条件 查询用户列表
     *
     * @param userName 用户名
     * @param userRole 用户角色
     * @param currentPageNum 当前页数
     * @param pageSize 页面大小
     * @return 用户列表
     */
    List<User> getUserList(String userName, int userRole, int currentPageNum, int pageSize);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 是否添加成功
     */
    boolean addUser(User user);

    /**
     * 查询用户编码是否已存在
     *
     * @param userCode 用户编码
     * @return 用户编码是否已存在
     */
    boolean isUserCodeExist(String userCode);

    /**
     * 通过用户编码 查询用户
     *
     * @param id 用户id
     * @return 用户
     */
    User getUser(Integer id);

    /**
     * 更新用户
     *
     * @param user user
     * @return 是否更新成功
     */
    boolean updateUser(User user);
}
