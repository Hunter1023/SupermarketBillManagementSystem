package com.hunter.servlet.user;

import com.alibaba.fastjson2.JSONArray;
import com.hunter.pojo.Role;
import com.hunter.pojo.User;
import com.hunter.service.role.RoleService;
import com.hunter.service.role.RoleServiceImpl;
import com.hunter.service.user.UserService;
import com.hunter.service.user.UserServiceImpl;
import com.hunter.util.Constants;
import com.hunter.util.PageSupport;
import com.mysql.cj.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            } else if (method.equals("query")) {
                query(req, resp);
            } else if (method.equals("add")) {
                addUser(req, resp);
            } else if (method.equals("getRoleList")) {
                getRoleList(req, resp);
            } else if (method.equals("userCodeExist")) {
                checkUserCodeExist(req, resp);
            }
        }
    }

    private void checkUserCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userCode = req.getParameter("userCode");

        UserService userService = new UserServiceImpl();
        boolean isUserCodeExist = userService.isUserCodeExist(userCode);

        Map<String, String> resultMap = new HashMap<>();
        if (isUserCodeExist) {
            resultMap.put(Constants.USER_CODE, "exist");
        } else {
            resultMap.put(Constants.USER_CODE, "notExist");
        }

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        // JSONArray fastjson的工具类，用于转换格式
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();
    }

    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        // JSONArray fastjson的工具类，用于转换格式
        writer.write(JSONArray.toJSONString(roleList));
        writer.flush();
        writer.close();
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isSucceed = true;
        String userCode = null;
        String userName = null;
        String userPassword = null;
        Integer gender = null;
        Date birthday = null;
        String phone = null;
        String address = null;
        Integer userRole = null;
        try {
            userCode = req.getParameter("userCode");
            userName = req.getParameter("userName");
            userPassword = req.getParameter("userPassword");

            gender = Integer.parseInt(req.getParameter("gender"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthday = dateFormat.parse(req.getParameter("birthday"));

            phone = req.getParameter("phone");
            address = req.getParameter("address");

            userRole = Integer.parseInt(req.getParameter("userRole"));
        } catch (ParseException e) {
            isSucceed = false;
            e.printStackTrace();
        }
        if (isSucceed) {
            User user = new User();
            user.setUserCode(userCode);
            user.setUserCode(userName);
            user.setUserPassword(userPassword);
            user.setGender(gender);
            user.setBirthday(birthday);
            user.setPhone(phone);
            user.setAddress(address);
            user.setUserRole(userRole);

            UserService userService = new UserServiceImpl();
            isSucceed = userService.addUser(user);
        }

        if (isSucceed) {
            req.setAttribute(Constants.MESSAGE, "添加新用户成功");
        } else {
            req.setAttribute(Constants.MESSAGE, "添加新用户失败");
        }
        req.getRequestDispatcher("userlist.jsp").forward(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) {
        // 从前端获取数据
        String queryUserName = req.getParameter("queryName");
        String queryUserRole = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");

        // 默认角色为0
        int userRole = Constants.DEFAULT_USER_ROLE;
        int pageSize = Constants.DEFAULT_PAGE_SIZE;
        int currentPageNum = Constants.DEFAULT_PAGE_NUM;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (!StringUtils.isNullOrEmpty(queryUserRole)) {
            userRole = Integer.parseInt(queryUserRole);
        }
        if (!StringUtils.isNullOrEmpty(pageIndex)) {
            currentPageNum = Integer.parseInt(pageIndex);
        }

        UserService userService = new UserServiceImpl();
        // 用户总数
        int totalCount = userService.getUserCount(queryUserName, userRole);

        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNum(currentPageNum);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = pageSupport.getTotalPageCount();
        // 控制首页和尾页
        if (totalPageCount < 1) {
            currentPageNum = 1;
        } else if (currentPageNum > totalPageCount) {
            currentPageNum = totalPageCount;
        }

        // 获取用户列表展示
        List<User> userList = userService.getUserList(queryUserName, userRole, currentPageNum, pageSize);

        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();

        req.setAttribute("userList", userList);
        req.setAttribute("roleList", roleList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNum", currentPageNum);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);

        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
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
