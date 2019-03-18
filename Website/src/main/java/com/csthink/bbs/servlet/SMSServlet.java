package com.csthink.bbs.servlet;


import com.csthink.bbs.service.SMSService;
import com.csthink.bbs.utils.JsonUtils;
import com.csthink.bbs.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SMSServlet extends HttpServlet {

    private int expire; // 验证码过期时间(秒)

    private String sessionKey;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionKey = config.getServletContext().getInitParameter("SMS_REG_SESSION_KEY");
        expire = Integer.valueOf(config.getServletContext().getInitParameter("SMS_REG_EXPIRE_TIME"));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/sendSMS.do".equals(req.getServletPath())) { // 发送短信验证码
            Map<String, Object> data = new HashMap<>();
            boolean flag = false;
            String msg = "验证码发送失败,请重新发送";
            String phone = req.getParameter("phone"); // 获取要发送的手机号

            if (StringUtils.isEmpty(phone)) {
                msg = "手机号无效，请重新填写";
            } else {
                String smsRegCode = String.valueOf(new Random().nextInt(8999) + 1000); // 生成四位验证码
                SMSService smsService = new SMSService();
                //String smsContent = "【小不点】验证码 " + smsRegCode + ",用于注册，10分钟内有效。验证码提供给他人可能导致账号被盗，请勿泄露，谨防被骗。";
                String smsContent = "小不点验证码 " + smsRegCode + ",用于注册，10分钟内有效。验证码提供给他人可能导致账号被盗，请勿泄露，谨防被骗。";
                Map<String, Object> resultMap = smsService.send(phone, smsContent); // 调用短信发送服务

                if (null != resultMap.get("code") && Integer.valueOf(resultMap.get("code").toString()) == 200) {
                    flag = true;
                    msg = "验证码已发送";
                }
                // 将验证码存到session中,同时存入创建时间
                VerifyCodeUtils.saveToSession(req.getSession(), smsRegCode, sessionKey);
            }

            data.put("flag", flag);
            data.put("msg", msg);
            JsonUtils.json(resp, data);
        } else if ("/checkSMS.do".equals(req.getServletPath())) { // 校验短信验证码
            String code = req.getParameter("code"); // 获取用户输入的验证码
            // 校验验证码是否正确
            Map<String, Object> resultMap = VerifyCodeUtils.checkCode(code, req.getSession(), sessionKey, expire);
            JsonUtils.json(resp, resultMap);
        }
    }
}
