package com.hunter.filter;


import com.hunter.util.Constants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SysFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 过滤器，从Session中获取用户
        if (req.getSession().getAttribute(Constants.USER_SESSION) == null) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
