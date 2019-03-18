package com.csthink.bbs.servlet;

import com.csthink.bbs.entity.User;
import com.csthink.bbs.service.SMSService;
import com.csthink.bbs.service.UserService;
import com.csthink.bbs.utils.CommonUtils;
import com.csthink.bbs.utils.JsonUtils;
import com.csthink.bbs.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterServlet extends HttpServlet {

    private int expire; // 验证码过期时间(秒)

    private String sessionKey;

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = new UserService();
        sessionKey = config.getServletContext().getInitParameter("SMS_REG_SESSION_KEY");
        expire = Integer.valueOf(config.getServletContext().getInitParameter("SMS_REG_EXPIRE_TIME"));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/register_prompt.do".equals(req.getServletPath())) { // 渲染请求注册页面
            req.getRequestDispatcher("/WEB-INF/views/biz/register.jsp").forward(req, resp);
        } else if ("/register.do".equals(req.getServletPath())) { // 处理注册用户操作
            String phone = req.getParameter("phone");
            String smsCode = req.getParameter("smsCode");
            Map<String, Object> data = new HashMap<>();
            String msg;
            boolean flag = false;

            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode)) {
                msg = "注册失败，缺失参数";
            } else {
                // 检查短信验证码是否正确
                Map<String, Object> checkMap = VerifyCodeUtils.checkCode(smsCode, req.getSession(), sessionKey, expire);
                if (null == checkMap.get("flag") || !(Boolean) checkMap.get("flag")) {
                    msg = checkMap.get("msg").toString();
                } else {
                    // 新注册用户自动创建临时用户名和临时密码
                    String username = CommonUtils.generateRandomUsername();
                    String passwordEncrypted = null;
                    String passwordNotEncrypted = CommonUtils.generateRandomPassword(6);
                    try {
                        passwordEncrypted = CommonUtils.encryptByMD5(CommonUtils.generateRandomPassword(6));
                    } catch (NoSuchAlgorithmException e) {
                        logger.info("密码加密失败,加密前: " + passwordNotEncrypted);
                    }

                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(passwordEncrypted);
                    user.setPhone(phone);

                    boolean result = userService.addUser(user);
                    if (!result) {
                        msg = "注册失败,请重新注册";
                    } else {
                        flag = true;
                        msg = "注册成功";
                        // 将用户信息写入session
                        req.getSession().setAttribute("user", user);
                        // 短信通知用户临时用户名和密码，提醒修改
                        SMSService smsService = new SMSService();
                        String smsContent = "小不点提醒您，您已注册成功，可以直接使用该手机号登录，系统已为您生成临时密码: "+ passwordNotEncrypted +" ,请妥善保管，并尽快在登录后及时修改密码。";
                        Map<String, Object> resultMap = smsService.send(phone, smsContent); // 调用短信发送服务
                        if (null != resultMap.get("code") && Integer.valueOf(resultMap.get("code").toString()) == 200) {
                            logger.info("新用户注册成功 " + phone + ", 临时密码已发送");
                        }
                    }
                }
            }

            data.put("flag", flag);
            data.put("msg", msg);
            System.out.println("data: " + data);
            JsonUtils.json(resp, data);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        userService = null;
    }
}
