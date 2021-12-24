package cn.edu.swu.ffdy.springboot.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ValidateCode {
    private static final String CHARS = "1234567890abcdefghijklmnopqrstuvwxyz";
    private static final int CODE_SIZE = 4; // 验证码个数
    private static final int PIC_WIDTH = 300; // 图片宽度
    private static final int PIC_HEIGHT = 100; // 图片高度
    private static final int FONT_COLOR_RGB = 100; // 字符颜色，0-255，数值越小颜色越深
    private static final int LINE_COLOR_RGB = 150; // 干扰线颜色，0-255，数值越小颜色越深
    private static final Random random = new Random();

    /**
     * 获取随机字符串
     *
     */
    public static String getCodeNum() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_SIZE; i++) {
            int index = random.nextInt(CHARS.length());
            char c = CHARS.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 得到验证码图片
     *
     */
    public static BufferedImage getCodePic(String codeNum) {
        BufferedImage bi = new BufferedImage(PIC_WIDTH, PIC_HEIGHT,
                BufferedImage.TYPE_INT_BGR); // 画布
        Graphics g = bi.getGraphics(); // 画笔
        g.fillRect(0, 0, PIC_WIDTH, PIC_HEIGHT); // 绘制矩形
        // 绘制干扰线
        drowLine(g);
        // 绘制字符串
        drawCode(g, codeNum);
        g.dispose(); // 释放资源
        return bi;
    }

    /*
     * 绘制字符串
     */
    private static void drawCode(Graphics g, String codeNum) {
        int fontSize;
        fontSize = PIC_WIDTH / CODE_SIZE;
        g.setFont(new Font("Fixedsys", Font.BOLD, fontSize)); // 字体

        for (int i = 0; i < codeNum.length(); i++) {
            g.setColor(getRandomColor());
            int rightX = (PIC_WIDTH / CODE_SIZE - fontSize / 2) / 2;
            int deviationX = random.nextInt(3);
            int deviationY = random.nextInt(3);
            g.translate(deviationX, deviationY);
            g.drawString(codeNum.charAt(i) + "", (PIC_WIDTH / CODE_SIZE) * i
                    + rightX - deviationX, (PIC_HEIGHT + fontSize / 2) / 2
                    - deviationY);
        }
    }

    /**
     * 获取随机颜色
     *
     */
    private static Color getRandomColor() {
        return new Color(random.nextInt(ValidateCode.FONT_COLOR_RGB), random.nextInt(ValidateCode.FONT_COLOR_RGB),
                random.nextInt(ValidateCode.FONT_COLOR_RGB));
    }

    /*
     * 绘制干扰线
     */
    private static void drowLine(Graphics g) {
        g.setColor(new Color(LINE_COLOR_RGB, LINE_COLOR_RGB, LINE_COLOR_RGB)); // 画笔颜色
        for (int i = 0; i < PIC_WIDTH * PIC_HEIGHT / 50; i++) {
            int x = random.nextInt(PIC_WIDTH); // 起点横坐标
            int y = random.nextInt(PIC_HEIGHT);
            int xl = random.nextInt(13); // 终点横坐标
            int yl = random.nextInt(15);
            g.drawLine(x, y, x + xl, y + yl);
        }

    }

    // 测试
    public static void main(String[] args) {
        System.out.println(getCodeNum());
        File file = new File("a.png");
        try {
            ImageIO.write(getCodePic(getCodeNum()), "png", file);
            System.out.println("成功 : \t" + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("失败");
        }
    }

}
