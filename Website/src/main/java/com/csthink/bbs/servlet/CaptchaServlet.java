package com.csthink.bbs.servlet;

import com.csthink.bbs.utils.CaptchaUtils;
import com.csthink.bbs.utils.JsonUtils;
import com.csthink.bbs.utils.VerifyCodeUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 图形验证码操作类
 */
public class CaptchaServlet extends HttpServlet {

    private int expire; // 验证码过期时间(秒)

    private String sessionKey;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionKey = config.getServletContext().getInitParameter("CAPTCHA_SESSION_KEY");
        expire = Integer.valueOf(config.getServletContext().getInitParameter("CAPTCHA_EXPIRE_TIME"));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 生成图形验证码
        if ("/captcha.do".equals(req.getServletPath())) {
            // 禁止图像缓存
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType("image/jpeg");

            CaptchaUtils captchaUtils = new CaptchaUtils(120, 56);
            String captchaCode = captchaUtils.getCode(); // 获取验证码内容
            // 将验证码存到session中,同时存入创建时间
            VerifyCodeUtils.saveToSession(req.getSession(), captchaCode, sessionKey);
            try {
                captchaUtils.getImage(resp.getOutputStream()); // 输出验证码图像
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("/checkCaptcha.do".equals(req.getServletPath())) { // 校验图形验证码功能
            String captchaCode = req.getParameter("code"); // 获取客户端输入的验证码
            // 校验验证码是否正确
            Map<String, Object> resultMap = VerifyCodeUtils.checkCode(captchaCode, req.getSession(), sessionKey, expire);
            JsonUtils.json(resp, resultMap);
        }
    }

}
