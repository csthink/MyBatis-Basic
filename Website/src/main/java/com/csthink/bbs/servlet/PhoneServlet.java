package com.csthink.bbs.servlet;

import com.alibaba.fastjson.JSONObject;
import com.csthink.bbs.entity.User;
import com.csthink.bbs.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PhoneServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        resp.setContentType("text/html;charset=utf-8");
        boolean flag = false;

        String phone = req.getParameter("phone");

        if (null != phone && !"".equals(phone.trim())) {
            List<User> userList = userService.findUserByPhone(phone);
            flag = userList.isEmpty();
        }

        jsonObject.put("flag", flag);
        resp.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void destroy() {
        super.destroy();
        userService = null;
    }
}
