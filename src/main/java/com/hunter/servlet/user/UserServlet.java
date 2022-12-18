package com.hunter.servlet.user;

import com.alibaba.fastjson2.JSONArray;
import com.hunter.pojo.User;
import com.hunter.service.user.UserService;
import com.hunter.service.user.UserServiceImpl;
import com.hunter.util.Constants;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现Servlet复用
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null) {
            if (method.equals("savePwd")) {
                updatePwd(req, resp);
            } else if (method.equals("verifyOldPwd")) {
                verifyOldPwd(req, resp);
            }
        }
    }

    private void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从Session里面拿ID (为了代码执行效率，先不转换类型)
        Object session = req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newPassword");

        if (session != null && !StringUtils.isNullOrEmpty(newPassword)) {
            UserService userService = new UserServiceImpl();
            boolean isSucceed = userService.updatePwd(((User) session).getId(), newPassword);
            if (isSucceed) {
                req.setAttribute(Constants.MESSAGE, "密码修改成功，请重新登录");
                // 移除当前session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute(Constants.MESSAGE, "密码修改失败");
            }
        } else {
            req.setAttribute(Constants.MESSAGE, "密码设置存在问题");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    private void verifyOldPwd(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object session = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldPassword = req.getParameter("oldPassword");

        // 结果集
        Map<String, String> resultMap = new HashMap<>();

        if (session == null) { // session失效了
            resultMap.put(Constants.RESULT, "sessionError");
        } else if (StringUtils.isNullOrEmpty(oldPassword)) { // 输入的旧密码为空
            resultMap.put(Constants.RESULT, "error");
        } else {
            String userPassword = ((User) session).getUserPassword();
            if (oldPassword.equals(userPassword)) {
                resultMap.put(Constants.RESULT, "true");
            } else {
                resultMap.put(Constants.RESULT, "false");
            }
        }

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        // JSONArray fastjson的工具类，用于转换格式
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
