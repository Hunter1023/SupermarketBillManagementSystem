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
}
