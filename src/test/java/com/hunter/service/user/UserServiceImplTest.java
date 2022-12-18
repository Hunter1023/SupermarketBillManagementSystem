package com.hunter.service.user;

import com.hunter.pojo.User;
import com.hunter.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceImplTest {
    private UserServiceImpl userService = new UserServiceImpl();

    @Test
    public void should_get_user_when_given_correct_userCode_and_passwd() {
        // when
        User admin = userService.login("admin", "111111");

        // then
        Assertions.assertNotNull(admin);
    }

    @Test
    public void should_get_null_when_given_wrong_userCode_or_passwd() {
        // when
        User admin = userService.login("admin", "123456");

        // then
        Assertions.assertNull(admin);
    }

    @Test
    public void should_get_0_when_get_admin_role_user_count() {
        // when
        int userCnt = userService.getUserCount(null, Constants.ADMIN_ROLE);

        // then
        Assertions.assertEquals(0, userCnt);
    }

    @Test
    public void should_get_4_when_get_manager_role_user_count() {
        // when
        int userCnt = userService.getUserCount(null, Constants.MANAGER_ROLE);

        // then
        Assertions.assertEquals(4, userCnt);
    }

    @Test
    public void should_get_8_when_get_employee_role_user_count() {
        // when
        int userCnt = userService.getUserCount(null, Constants.EMPLOYEE_ROLE);

        // then
        Assertions.assertEquals(8, userCnt);
    }
}
