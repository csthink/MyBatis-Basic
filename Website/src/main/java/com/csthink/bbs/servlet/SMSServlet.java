package com.csthink.bbs.servlet;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SMSServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject jsonObject = new JSONObject();
        resp.setContentType("text/html;charset=utf-8");
        boolean flag = false;

        if ("/sendSMS.do".equals(req.getServletPath())) {
            // 发送短信验证码
            // TODO 实现短信发送服务
            String msgVerifyCode = "1111";
            flag = true;

            // 将生成的验证码字符串保存到session
            HttpSession session = req.getSession(true);
            // 删除之前已保存到session中的验证码
            if (null != session.getAttribute("msgVerifyCode")) {
                session.removeAttribute("msgVerifyCode");
            }

            // 将短信验证码写入session
            session.setAttribute("msgVerifyCode", msgVerifyCode);

            jsonObject.put("flag", flag);
            jsonObject.put("msg", "短信发送成功");
        } else if ("/checkSMS.do".equals(req.getServletPath())) {
            // 校验短信验证码
            String code = req.getParameter("code");
            String msg = "";

            // 校验验证码是否正确
            if (null != code && !"".equals(code.trim())) {
                if (code.equalsIgnoreCase((String) req.getSession().getAttribute("msgVerifyCode"))) {
                    // 验证码正确
                    flag = true;
                    msg = "短信验证码有效";
                } else {
                    flag = false;
                    msg = "短信验证码无效";
                }
            }

            jsonObject.put("flag", flag);
            jsonObject.put("msg", msg);
        }

        resp.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
    }
}
