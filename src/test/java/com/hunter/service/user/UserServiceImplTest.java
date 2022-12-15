package com.hunter.service.user;

import com.hunter.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceImplTest {
    @Test
    public void should_get_user_when_given_correct_userCode_and_passwd() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "111111");
        Assertions.assertNotNull(admin);
    }

    @Test
    public void should_get_null_when_given_wrong_userCode_or_passwd() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "123456");
        Assertions.assertNull(admin);
    }
}
