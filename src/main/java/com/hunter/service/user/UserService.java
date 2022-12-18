package com.hunter.service.user;

import com.hunter.pojo.User;

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
}
