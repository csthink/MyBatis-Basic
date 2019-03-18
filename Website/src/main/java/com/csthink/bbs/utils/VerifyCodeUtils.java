/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/18 06:23:18
 * LastModified: 2019/03/18 06:23:18
 */

package com.csthink.bbs.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VerifyCodeUtils {

    /**
     * 校验验证码是否有效
     *
     * @param code       用户输入的验证码
     * @param session    session 会话对象
     * @param sessionKey session存储的验证码key
     * @param expireTime session 有效时间
     * @return resultMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> checkCode(String code, HttpSession session, String sessionKey, int expireTime) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> storedMap;
        boolean flag = false;
        String msg;

        // 这里处理的有点欠妥，后期可考虑记log
        if (null == session.getAttribute(sessionKey)) {
            msg = "验证码获取失败，请重新获取";
        } else if (StringUtils.isEmpty(code)) {
            msg = "验证码不能为空，请重新输入";
        } else {
            storedMap = (Map<String, Object>) session.getAttribute(sessionKey); // 读取session中保存的验证码
            String realCode = String.valueOf(storedMap.get("text")); // 获取session保存的验证码
            Date now = new Date();
            long currentTime = now.getTime(); // 获取当前时间
            Long captchaTime = Long.valueOf(storedMap.get("time") + ""); // 获取验证码的创建时间

            if ((currentTime - captchaTime) / 1000 > expireTime) {
                msg = "验证码已失效，请重新输入！";
            } else if (!code.equalsIgnoreCase(realCode)) {
                // 验证码不匹配
                msg = "验证码错误，请重新输入";
            } else {
                flag = true;
                msg = "验证通过";
            }
        }

        resultMap.put("flag", flag);
        resultMap.put("msg", msg);

        return resultMap;
    }

    /**
     * 将验证码存到session中,同时存入创建时间
     *
     * @param session    session 对象
     * @param code       验证码
     * @param sessionKey session标识
     */
    public static void saveToSession(HttpSession session, String code, String sessionKey) {
        // 将生成的验证码字符串保存到session
        // 删除之前已保存到session中的验证码
        if (null != session.getAttribute(sessionKey)) {
            session.removeAttribute(sessionKey);
        }

        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put("text", code); // 验证码内容
        sessionMap.put("time", new Date().getTime()); // 验证码创建时间
        // 将短信验证码写入session
        session.setAttribute(sessionKey, sessionMap);
    }
}
