package com.csthink.bbs.servlet;

import com.alibaba.fastjson.JSONObject;
import com.csthink.bbs.utils.CaptchaUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 图形验证码操作类
 */
public class CaptchaServlet extends HttpServlet {

    private final int EXPIRE_TIME = 60; // 验证码过期时间(秒)

    private final String CAPTCHA_SESSION_KEY = "captcha";

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
            String vcode = captchaUtils.getCode(); // 获取验证码内容

            // 将生成的验证码字符串保存到session
            HttpSession session = req.getSession(true);
            // 删除之前已保存到session中的验证码
            if (null != session.getAttribute(CAPTCHA_SESSION_KEY)) {
                session.removeAttribute(CAPTCHA_SESSION_KEY);
            }

            Map<String, Object> captchaMap = new HashMap<>();
            captchaMap.put("text", vcode); // 验证码内容
            captchaMap.put("time", new Date().getTime()); // 验证码创建时间
            session.setAttribute(CAPTCHA_SESSION_KEY, captchaMap);
            System.out.println(captchaMap);

            try {
                captchaUtils.getImage(resp.getOutputStream()); // 输出验证码图像
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("/checkCaptcha.do".equals(req.getServletPath())) {
            // 校验图形验证码功能
            JSONObject jsonObject = new JSONObject();
            resp.setContentType("text/html;charset=utf-8");

            String code = req.getParameter("code"); // 获取客户端输入的验证码
            Map<String, Object> resultMap = verifyCaptcha(code, req.getSession());
            if (null != resultMap) {
                jsonObject.put("flag", resultMap.get("flag"));
                jsonObject.put("msg", resultMap.get("msg"));
                resp.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * 校验验证码是否有效
     *
     * @param code    用户输入的验证码
     * @param session session 会话对象
     * @return Map类型集合
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> verifyCaptcha(String code, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> captchaMap;
        boolean flag = false;
        String msg;

        // 这里处理的有点欠妥，后期可考虑记log
        captchaMap = (Map<String, Object>) session.getAttribute(CAPTCHA_SESSION_KEY); // 读取session中保存的验证码
        String realCode = String.valueOf(captchaMap.get("text")); // 获取session保存的验证码
        Date now = new Date();
        long currentTime = now.getTime(); // 获取当前时间
        Long captchaTime = Long.valueOf(captchaMap.get("time") + ""); // 获取验证码的创建时间

        // 校验验证码是否正确
        if (null == code || "".equals(code.trim()) || captchaMap.isEmpty()) {
            // 验证码无效
            msg = "验证码不能为空，请重新输入";
        } else if ((currentTime - captchaTime) / 1000 > EXPIRE_TIME) {
            msg = "验证码已失效，请重新输入！";
        } else if (!code.equalsIgnoreCase(realCode)) {
            // 验证码不匹配
            msg = "验证码错误，请重新输入";
        } else {
            flag = true;
            msg = "验证通过";
        }

        resultMap.put("flag", flag);
        resultMap.put("msg", msg);

        return resultMap;
    }
}
