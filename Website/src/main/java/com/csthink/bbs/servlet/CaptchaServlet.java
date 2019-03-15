package com.csthink.bbs.servlet;

import com.alibaba.fastjson.JSONObject;
import com.csthink.bbs.utils.CaptchaUtils;
import com.csthink.bbs.utils.ImageValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.json.JSONObject;

/**
 * 生成图形验证码
 */
public class ImageValidationServlet extends HttpServlet {

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
            if (null != session.getAttribute("captchaMap")) {
                session.removeAttribute("captchaMap");
            }

            Map<String, Object> captchaMap = new HashMap<>();
            captchaMap.put("text", vcode.toLowerCase()); // 验证码内容
            captchaMap.put("time", new Date().getTime()); // 验证码创建时间
            session.setAttribute("captcha", captchaMap);
            System.out.println(captchaMap);

            try {
                captchaUtils.getImage(resp.getOutputStream()); // 输出验证码图像
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            // 设置验证码图片尺寸
            ImageValidationUtils.IMAGE_WIDTH = 120;
            ImageValidationUtils.IMAGE_HEIGHT = 56;
            // 生成随机的验证码字串
            String verifyCode = ImageValidationUtils.generateVerifyCode();
            // 将生成的验证码字符串保存到session
            HttpSession session = req.getSession(true);
            // 删除之前已保存到session中的验证码
            if (null != session.getAttribute("verifyCode")) {
                session.removeAttribute("verifyCode");
            }

            // 将验证码转换小写后写入session
            session.setAttribute("verifyCode", verifyCode.toLowerCase());
            ImageValidationUtils.outputImage(resp.getOutputStream(), verifyCode);
            */
        } else if ("/checkCaptcha.do".equals(req.getServletPath())) {
            // 校验图形验证码功能
            JSONObject jsonObject = new JSONObject();
            resp.setContentType("text/html;charset=utf-8");

            String code = req.getParameter("code"); // 获取客户端输入的验证码
            Map<String, Object> resultMap =  verifyCaptcha(code, req.getSession());
            if (null != resultMap) {
                jsonObject.put("flag", resultMap.get("flag"));
                jsonObject.put("msg", resultMap.get("msg"));
                resp.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private Map<String, Object> verifyCaptcha(String code, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> captchaMap;
        boolean flag = false;
        String msg = "";

        captchaMap = (Map<String, Object>) session.getAttribute("captchaMap"); // 读取session中保存的验证码
        Date now = new Date();
        long currentTime = now.getTime(); // 获取当前时间
        Long captchaTime = Long.valueOf(captchaMap.get("time") + ""); // 获取验证码的创建时间
        String realCode = String.valueOf(captchaMap.get("text")); // 获取session保存的验证码

        // 校验验证码是否正确
        if (null == code || "".equals(code.trim()) || captchaMap.isEmpty()) {
            // 验证码无效
            msg = "验证码不能为空，请重新输入";
        } else if ((currentTime - captchaTime) / 1000 / 60 > 1) {
            // 验证码有效期设置为1分钟
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
