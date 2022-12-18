package com.hunter.servlet.user;

import com.hunter.pojo.User;
import com.hunter.service.user.UserService;
import com.hunter.service.user.UserServiceImpl;
import com.hunter.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet：控制层，调用业务层代码
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取用户名和密码，参数名根据login.jsp中的填写
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        // 调用业务层，和数据库中的密码进行对比
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user != null) {
            // 将用户信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            //跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            // 转发回登录页面，并提示用户名或密码错误
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
