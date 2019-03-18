/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/18 15:17:18
 * LastModified: 2019/03/18 15:17:18
 */

package com.csthink.bbs.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (null != request.getSession().getAttribute("user")) {
            request.getSession().setAttribute("user", null);
        }
        response.sendRedirect("/message/list.do");
    }
}
