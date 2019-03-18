/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/18 06:41:18
 * LastModified: 2019/03/18 06:41:18
 */

package com.csthink.bbs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.toJSONString;

public class JsonUtils {

    /**
     * 默认字符编码
     */

    private static String encoding = "UTF-8";

    /**
     * JSONP默认的回调函数
     */
    private static String callback = "callback";

    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        // compatible with the java.util.Date and the java.sql.Date
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    /**
     * FastJSON的序列化设置
     */
    private static final SerializerFeature[] features = {
            // 输出Map中为Null的值
            SerializerFeature.WriteMapNullValue,
            // 如果List为Null，则输出为[]
            SerializerFeature.WriteNullListAsEmpty,
            // 如果Boolean对象为Null，则输出为false
            SerializerFeature.WriteNullBooleanAsFalse,
            // 输出Null字符串
            SerializerFeature.WriteNullStringAsEmpty,
            // 如果Number为Null，则输出为0
            SerializerFeature.WriteNullNumberAsZero,
            // 格式化输出日期
            //SerializerFeature.WriteDateUseDateFormat
    };

    /**
     * 返回JSON格式数据
     *
     * @param response
     * @param data     待返回的Java对象
     * @param encoding 返回JSON字符串的编码格式
     */
    public static void json(HttpServletResponse response, Object data, String encoding) {
        //设置编码格式
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(toJSONString(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回JSON格式数据，使用默认编码
     *
     * @param response
     * @param data     待返回的Java对象
     */
    public static void json(HttpServletResponse response, Object data) {
        json(response, data, encoding);
    }

    /**
     * 返回JSONP数据，使用默认编码和默认回调函数
     *
     * @param response
     * @param data     JSONP数据
     */
    public static void jsonp(HttpServletResponse response, Object data) {
        jsonp(response, callback, data, encoding);
    }

    /**
     * 返回JSONP数据，使用默认编码
     *
     * @param response
     * @param callback JSONP回调函数名称
     * @param data     JSONP数据
     */
    public static void jsonp(HttpServletResponse response, String callback, Object data) {
        jsonp(response, callback, data, encoding);
    }

    /**
     * 返回JSONP数据
     *
     * @param response
     * @param callback JSONP回调函数名称
     * @param data     JSONP数据
     * @param encoding JSONP数据编码
     */
    public static void jsonp(HttpServletResponse response, String callback, Object data, String encoding) {
        StringBuilder sb = new StringBuilder(callback);
        sb.append("(");
        sb.append(toJSONString(data));
        sb.append(");");

        // 设置编码格式
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * list to json
     *
     * @param list the list that will transform to json string
     * @return the json string of list transform
     */
    public static String list2json(List list) {
        return toJSONString(list);
    }

    /**
     * map to json
     *
     * @param map the map that will transform to json string
     * @return the json string of map transform
     */
    public static String map2json(Map map) {
        return toJSONString(map);
    }

    /**
     * object array to json
     *
     * @param objects the object array that will transform to json string
     * @return the json string of array transform
     */
    public static String array2json(Object[] objects) {
        return toJSONString(objects);
    }

    /**
     * object to json
     *
     * @param object the object that will transform to json string
     * @return the json string of object
     */
    public static String object2json(Object object) {
        return toJSONString(object, config, features);
    }


    /**
     * json to list
     *
     * @param json  the json string that will transform to list
     * @param clazz the class of the list's element
     * @param <T>   the generic of the class
     * @return the list that json string transform
     */
    public static <T> List<T> json2list(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * json to map
     *
     * @param json json string that will transform to map
     * @return the map fo json string
     */
    public static Map json2map(String json) {
        return JSONObject.parseObject(json);
    }


    /**
     * json string to object array
     *
     * @param json  the json string will transform to object array
     * @param clazz the class of the json will transform
     * @param ts    the real object array
     * @param <T>   the real object
     * @param json
     * @param clazz
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> T[] json2array(String json, Class<T> clazz, T[] ts) {
        return JSON.parseArray(json, clazz).toArray(ts);
    }

    /**
     * json string to object
     *
     * @param json  the json string that will transform to object
     * @param clazz the class that json will transform
     * @param <T>   the object class
     * @return the object of json string
     */
    public static <T> Object json2object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        JsonUtils.encoding = encoding;
    }

    public static String getCallback() {
        return callback;
    }

    public static void setCallback(String callback) {
        JsonUtils.callback = callback;
    }
}
