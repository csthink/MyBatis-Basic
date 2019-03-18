/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/18 15:37:18
 * LastModified: 2019/03/18 15:37:18
 */

package com.csthink.bbs.servlet;

import com.csthink.bbs.entity.User;
import com.csthink.bbs.service.UserService;
import com.csthink.bbs.utils.CommonUtils;
import com.csthink.bbs.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (null != req.getSession().getAttribute("user")) {
            resp.sendRedirect("/message/list.do");
            return;
        }

        if ("/login_prompt.do".equals(req.getServletPath())) { // 渲染登录页面
            req.getRequestDispatcher("/WEB-INF/views/biz/login.jsp").forward(req, resp);
        } else if ("/login.do".equals(req.getServletPath())) {
            int expire = Integer.valueOf(req.getServletContext().getInitParameter("SMS_LOGIN_EXPIRE_TIME"));
            String sessionKey = req.getServletContext().getInitParameter("SMS_LOGIN_SESSION_KEY");
            String type = req.getParameter("type");
            String phone = req.getParameter("phone");
            String smsCode = req.getParameter("smsVerifyCode");
            String password = req.getParameter("password");
            String errorMsg = "";
            boolean smsLogin = ("sms".equals(type) && !StringUtils.isEmpty(smsCode));
            boolean pwdLogin = ("pwd".equals(type) && !StringUtils.isEmpty(password));
            List<User> user = null;

            if (StringUtils.isEmpty(type) || StringUtils.isEmpty(phone)) {
                errorMsg = "参数缺失";
            } else {
                if (smsLogin || pwdLogin) {
                    user = userService.findUserByPhone(phone);
                    if (user.isEmpty()) {
                        errorMsg = "该手机号尚未注册，请注册后再登录";
                    } else {
                        if (smsLogin) { // 短信登录
                            // 检查短信验证码是否正确
                            Map<String, Object> checkMap = VerifyCodeUtils.checkCode(smsCode, req.getSession(), sessionKey, expire);
                            if (null == checkMap.get("flag") || !(Boolean) checkMap.get("flag")) {
                                errorMsg = checkMap.get("msg").toString();
                            } else {
                                errorMsg = "";
                            }
                        } else { // 密码登录
                            try {
                                if (!CommonUtils.checkPassword(password, user.get(0).getPassword())) {
                                    errorMsg = "用户名或密码不正确，请重新输入";
                                } else {
                                    errorMsg = "";
                                }
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            if (!StringUtils.isEmpty(errorMsg)) {
                req.setAttribute("errorMsg", errorMsg);
                req.getRequestDispatcher("/login_prompt.do").forward(req, resp);
            } else {
                // 将用户信息写入session
                if (null != user) {
                    req.getSession().setAttribute("user", user.get(0));
                }
                resp.sendRedirect("/message/list.do");
            }
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        userService = null;
    }
}
