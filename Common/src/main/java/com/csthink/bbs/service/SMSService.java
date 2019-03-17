/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/17 06:03:17
 * LastModified: 2019/03/17 06:03:17
 */

package com.csthink.bbs.service;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SMSService {

    private String appId;

    private String appSecret;

    private String apiUrl;

    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

//    public static void main(String[] args) {
//        logger.trace("Trace Level");
//        logger.debug("Debug Level");
//        logger.info("Info Level");
//        logger.warn("Warn Level");
//        logger.error("Error Level");

//        JSONObject json = null;
//        SMSService service = new SMSService();
//        String result = service.send("15056992627","测试短信");
//        json = JSONObject.parseObject(result);
//
//        if (json.getIntValue("code") != 0) {
//            System.out.println("短信发送失败");
//            System.out.println(json.getString("data"));
//            System.out.println(json.getString("code"));
//        } else {
//            System.out.println("短信发送成功");
//        }
//    }

    public SMSService() {
        Properties properties = this.getProperties();
        this.appId = (String) properties.get("APP_ID");
        this.appSecret = (String) properties.get("APP_SECRET");
        this.apiUrl = (String) properties.get("API_URL");
    }

    /**
     * 发送单条短信
     * @param phone 手机号
     * @param content 短信内容
     * @return
     */
    public String send(String phone, String content) {
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        String result = "";
        JSONObject jsonObject = new JSONObject();

        try {
            // 参数无效
            if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(content)) {
                jsonObject.put("code", "-1");
                jsonObject.put("data", "参数缺失");
                result =  jsonObject.toString();
            } else {
                result = client.send(phone, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 读取properties配置方式一 >>> 基于ClassLoader读取配置文件
     * 优点: 该方式只能读取类路径下的配置文件
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            is = this.getClass().getClassLoader().getResourceAsStream("sms.properties");
            isr = new InputStreamReader(is, StandardCharsets.UTF_8); // 解决中文乱码
            properties.load(isr);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }


    /**
     * 读取properties配置方式二 >>> 基于 InputStream 读取配置文件
     * 优点: 可以读取任意路径下的配置文件
     */
    /*
    private void getProperties() {
        Properties properties = new Properties();
        String basePath = System.getProperty("user.dir");
        String propertiesPath = basePath + File.separator + "Common" + File.separator + "src"
                + File.separator + "main" + File.separator + "resources" + File.separator + "sms.properties";

        FileInputStream fis = null;
        try {
            // 使用 InPutStream 流读取properties文件
            fis = new FileInputStream(propertiesPath);
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
    */
}
