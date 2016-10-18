package com.lcw.herakles.platform.common.util.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * file util
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class FileUtil {

    private FileUtil() {

    }

    /**
     * 重命名文件
     * 
     * @param fileName
     * @return 保存到FTP服务器的文件名称
     */
    public static String rename(String fileName) {
        StringBuilder resp = new StringBuilder();
        String newName = String.valueOf(System.currentTimeMillis());
        // RandomUtil.getAlnum(5);
        newName = newName + RandomStringUtils.randomNumeric(5);
        resp.append(newName);
        resp.append(".");
        resp.append(getSuffix(fileName).toLowerCase());
        return resp.toString();
    }

    /**
     * 获取文件后缀名
     * 
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.indexOf(".") + 1);
    }

    /**
     * BufferedImage转InputStream
     * 
     * @param bimage
     * @return
     */
    public static InputStream getImageStream(BufferedImage bimage) {
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png", imOut);
            is = new ByteArrayInputStream(bs.toByteArray());
        } catch (IOException e) {
            log.error("FtpUtil.getImageStream() error:", e);
        }
        return is;
    }

}
