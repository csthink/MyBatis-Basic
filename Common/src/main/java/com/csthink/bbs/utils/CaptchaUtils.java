/*
 * Copyright (c) 2013 - 2019. All Rights Reserved.
 * ProjectName: MyBatis-Basic
 * Author: csthink
 * Date: 2019/03/15 11:50:15
 * LastModified: 2019/03/15 11:50:15
 */

package com.csthink.bbs.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtils {

    /**
     * 验证码字符串
     */
    private String code;

    /**
     * 验证码图片宽度
     */
    private int width = 120;

    /**
     * 验证码图片高度
     */
    private int height = 40;

    /**
     * 验证码字符串个数
     */
    private int codeLength = 4;

    /**
     * 验证码图片的干扰线数
     */
    private int lineNum = 3;

    private String source = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";

    private static Random random = new Random();

    private BufferedImage bufferedImage;

    public CaptchaUtils() {
        this.generateImage();
    }

    public CaptchaUtils(int width, int height) {
        this.width = width;
        this.height = height;
        this.generateImage();
    }

    public CaptchaUtils(int width, int height, int codeLength, int lineNum) {
        this.width = width;
        this.height = height;
        this.codeLength = codeLength;
        this.lineNum = lineNum;
        this.generateImage();
    }

    /**
     * 输出图像
     * @param os
     * @throws IOException
     */
    public void getImage(OutputStream os) throws IOException {
        ImageIO.write(bufferedImage, "jpeg", os);
        os.close();
    }

    /*
    public static void main(String[] args) {
        CaptchaUtils captcha = new CaptchaUtils();

        try {
            String path = "/Users/csthink/Downloads/captcha/";
            System.out.println(captcha.getCode() + " : " + path);
            captcha.getImage(path, new Date().getTime() + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    /**
     * 生成验证码输出到文件夹，仅用于测试
     */
    /*
    public void getImage(String path, String fileName) throws IOException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        OutputStream os = new FileOutputStream(path + fileName);
        this.getImage(os);
    }
    */

    private void generateImage() {
        // 图像 buffer
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        // 设置背景色
        g2.setColor(getRandomColor(200, 250));
        g2.fillRect(0, 0, width, height);

        // 设置字体
        int fontWidth = width / codeLength; // 字体的宽度
        int fontHeight = height - 5;// 字体的高度
        g2.setColor(getRandomColor(100, 160));
        Font font = getFont(fontHeight);
        g2.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineNum; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g2.setColor(getRandomColor(1, 250)); // 设置线条的颜色
            g2.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点
        float yawpRate = 0.02f; // 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bufferedImage.setRGB(x, y, random.nextInt(255));
        }

        // 生成随机字符串
        String randomStr = getRandomStr();
        this.code = randomStr;
        int codeY = height - 8;
        // 画字符串
        for (int i = 0; i < codeLength; i++) {
            String strRand = randomStr.substring(i, i + 1);
            g2.setColor(getRandomColor(1, 255));
            g2.drawString(strRand, i * fontWidth + 3, codeY);
        }
    }

    /**
     * 生成随机字符串
     */
    private String getRandomStr() {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder randomCode = new StringBuilder(codeLength);
        int sourceLength = source.length();

        for (int i = 0; i < codeLength; i++) {
            randomCode.append(source.charAt(random.nextInt(sourceLength - 1)));
        }

        return randomCode.toString();
    }

    /**
     * 给定范围产生随机颜色
     *
     * @param fc
     * @param bc
     * @return 颜色对象
     */
    private Color getRandomColor(int fc, int bc) {
        fc = fc > 255 ? 255 : fc;
        bc = bc > 255 ? 255 : bc;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    private Font getFont(int size) {
        Font font[] = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Fixedsys", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);

        return font[random.nextInt(5)];
    }

    /**
     * 扭曲方法
     *
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    /**
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);

            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    /**
     * @param g
     * @param w1
     * @param h1
     * @param color
     */
    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);

            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }

    }

    public String getCode() {
        return code;
    }
}
