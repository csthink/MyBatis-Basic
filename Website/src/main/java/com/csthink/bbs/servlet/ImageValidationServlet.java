package com.csthink.bbs.servlet;

import com.csthink.bbs.utils.ImageValidationUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 生成图形验证码
 */
public class ImageValidationServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 生成图形验证码
        if ("/image_verify_code.do".equals(req.getServletPath())) {
            resp.setHeader("Pragma", "No-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);
            resp.setContentType("image/jpeg");

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
        } else if ("/checkImageVerifyCode.do".equals(req.getServletPath())) {
            // 校验图形验证码
            String code = req.getParameter("code");

            JSONObject jsonObject = new JSONObject();
            resp.setContentType("text/html;charset=utf-8");
            boolean flag = false;

            // 校验验证码是否正确
            if (null != code && !"".equals(code.trim())) {
                if (code.equalsIgnoreCase((String) req.getSession().getAttribute("verifyCode"))) {
                    // 验证码正确
                    flag = true;
                }
            }

            jsonObject.put("flag", flag);
            resp.getOutputStream().write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        }

    }
}
