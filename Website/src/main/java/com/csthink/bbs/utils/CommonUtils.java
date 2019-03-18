/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/18 10:52:18
 * LastModified: 2019/03/18 10:52:18
 */

package com.csthink.bbs.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class CommonUtils {

    /**
     * 生成随机用户名
     *
     * @return 用户名
     */
    public static String generateRandomUsername() {
        String username;

        Random random = new Random(System.currentTimeMillis());
        StringBuilder randomCode = new StringBuilder(3);
        String source = "abcdedfhjkmnpqrstuvwxyz23456789";

        for (int i = 0; i < 3; i++) {
            randomCode.append(source.charAt(random.nextInt(source.length() - 1)));
        }

        username = "csi_" + randomCode.toString();

        return username;
    }


    /**
     * 生成随机密码
     *
     * @param length 密码长度
     * @return 密码
     */
    public static String generateRandomPassword(int length) {
        String password;

        Random random = new Random(System.currentTimeMillis());
        StringBuilder randomCode = new StringBuilder(length);
        String source = "ABCDEFGHJKMNPQRSTUVWXYZabcdedfhjkmnpqrstuvwxyz!@#$%^&*()23456789";

        for (int i = 0; i < length; i++) {
            randomCode.append(source.charAt(random.nextInt(source.length() - 1)));
        }

        password = randomCode.toString();

        return password;
    }

    public static String encryptByMD5(String str) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(str.getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
        return new BigInteger(1, md.digest()).toString(16);
    }

    public boolean checkPassword(String newPwd, String oldPwd) throws NoSuchAlgorithmException {
        return encryptByMD5(newPwd).equals(oldPwd);
    }


}
