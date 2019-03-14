package com.csthink.bbs.servlet;

import com.csthink.bbs.entity.User;
import com.csthink.bbs.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/register_prompt.do".equals(req.getServletPath())) { // 渲染请求注册页面
            req.getRequestDispatcher("/WEB-INF/views/biz/register.jsp").forward(req, resp);
        } else if ("/register.do".equals(req.getServletPath())) { // 处理注册用户操作
            User user = new User();


            userService.addUser(user);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        userService = null;
    }
}
