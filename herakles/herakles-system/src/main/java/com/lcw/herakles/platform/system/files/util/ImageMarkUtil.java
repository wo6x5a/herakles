package com.lcw.herakles.platform.system.files.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.util.file.ftp.FtpUtil;

/**
 * 图片水印工具
 * 
 * @author chenwulou
 *
 */
public class ImageMarkUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMarkUtil.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        String srcImgPath = "d:/test.png";
        String targerPath2 = "d:/test_mark1_icon.png";
        // 给图片添加水印,水印旋转-45
        ImageMarkUtil.markImageByIcon(srcImgPath, targerPath2, -45);

        // String srcImgPath = "d:/test.png";
        // String logoText = "[ 测试文字水印 herakles ]";
        // String targerPath = "d:/test_mark_text.png";
        //
        // // 给图片添加水印,水印旋转-45
        // ImageMarkUtil.markByText(logoText, srcImgPath, targerPath, -45);

    }

    /**
     * 给图片添加水印
     * 
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            旋转角度
     */
    public static void markImageByIcon(String srcImgPath, String targerPath, Integer degree) {
        InputStream ins = getWaterImageInputStream();
        byte[] iconByteData = null;
        try {
            iconByteData = IOUtils.toByteArray(ins);
        } catch (IOException e) {
            LOGGER.error("ImageMarkUtil error, {}", e.getMessage());
        }
        markImageByIcon(iconByteData, srcImgPath, targerPath, degree);
    }

    private static InputStream getWaterImageInputStream() {
        String waterImage = getWaterImage();
        InputStream ins = ImageMarkUtil.class.getClassLoader().getResourceAsStream(waterImage);
        return ins;
    }

    private static String getWaterImage() {
        StringBuilder param = new StringBuilder();
        String pattern = ApplicationConsts.WATERMARK_PATTERN;
        // TODO name
        String customSource = "logo";
        if (StringUtils.isNotBlank(customSource)) {
            param.append("_");
            param.append(customSource.toLowerCase());
        }
        return MessageFormat.format(pattern, param.toString());
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * 
     * @param imageData
     *            水印图片数据
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            水印图片旋转角度
     */
    public static void markImageByIcon(byte[] imageData, String srcImgPath, String targerPath, Integer degree) {
        String suffix = FtpUtil.suffix(srcImgPath);
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0,
                    0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(imageData);

            // 得到Image对象。
            Image img = imgIcon.getImage();

            float alpha = 0.5f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 表示水印图片的位置
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Integer imgWidth = bufImg.getWidth();
            Integer imgHigh = bufImg.getHeight();
            for (int x = 0; x < imgWidth; x = x + 300) {
                for (int y = 0; y < imgHigh; y = y + 150) {
                    g.drawImage(img, x, y, null);
                }
            }

            // g.drawImage(img, 150, 300, null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, suffix, os);

        } catch (Exception e) {
            LOGGER.error("ImageMarkUtil error, {}", e.getMessage());
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                LOGGER.error("ImageMarkUtil error, {}", e.getMessage());
            }
        }
    }

    /**
     * 给图片添加文字水印、可设置水印的旋转角度
     * 
     * @param logoText
     *            水印文字
     * @param srcImgPath
     *            源图片路径
     * @param targerPath
     *            目标图片路径
     * @param degree
     *            旋转角度
     */
    public static void markByText(String logoText, String srcImgPath, String targerPath, Integer degree) {
        String suffix = FtpUtil.suffix(srcImgPath);
        // 主图片的路径
        InputStream is = null;
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象
            // Graphics g= buffImg.getGraphics();
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0,
                    0, null);

            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }

            // 设置颜色
            g.setColor(Color.red);

            // 设置 Font
            g.setFont(new Font("宋体", Font.BOLD, 30));

            float alpha = 0.5f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
            int srcImgWidth = srcImg.getWidth(null);
            int srcImgHeight = srcImg.getHeight(null);
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Integer imgWidth = bufImg.getWidth();
            Integer imgHigh = bufImg.getHeight();
            for (int x = 0; x < imgWidth; x = x + 300) {
                for (int y = 0; y < imgHigh; y = y + 150) {
                    g.drawString(logoText, x, y);
                }
            }
            // g.drawString(logoText, 150, 300);

            g.dispose();

            os = new FileOutputStream(targerPath);

            // 生成图片
            ImageIO.write(buffImg, suffix, os);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (null != is)
                    is.close();
            } catch (Exception e) {
                LOGGER.error("ImageMarkUtil error, {}", e.getMessage());
            }
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                LOGGER.error("ImageMarkUtil error, {}", e.getMessage());
            }
        }
    }
}
