package com.lcw.herakles.platform.common.util.file.ftp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.util.ApplicationContextUtil;
import com.lcw.herakles.platform.common.util.file.FileUtil;
import com.lcw.herakles.platform.common.util.file.ftp.pool.FtpClientPoolFactory;
import com.lcw.herakles.platform.common.util.file.image.ImageMarkUtil;

/**
 * Class Name: FtpUtil
 * 
 * Description: ftp工具类
 * 
 * @author chenwulou
 *
 */
public class FtpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    private FtpUtil() {

    }

    /**
     * Description: 上传文件
     *
     * @param fileName 文件名
     * @param input
     * @param filePath ftp相对目录
     */
    public static String upload(String fileName, InputStream input, String filePath,
            boolean ismark) {
        StringBuilder resp = new StringBuilder("");
        resp.append(filePath);
        // resp.append(File.separator);
        resp.append(fileName);
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            changeWorkingDirectory(ftpClient, filePath);
            if (ismark) {
                // 如果水印,图片输入流则为水印图片输入流
                BufferedImage buf = ImageMarkUtil.markImageByIcon(input, resp.toString(), -45);
                input = FileUtil.getImageStream(buf);
            }
            ftpClient.storeFile(fileName, input);
        } catch (Exception e) {
            LOGGER.error("文件上传失败, {}", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
        return resp.toString();
    }

    /**
     * Description: 删除文件
     * 
     * @param filePath : product/pic/111.png
     */
    public static void delete(String filePath) {
        File file = new File(filePath);
        String remotePath = file.getParent();
        String fileName = file.getName();

        delete(fileName, remotePath);
    }

    /**
     * Description: 删除文件
     *
     * @param fileName
     * @param filePath
     */
    public static void delete(String fileName, String filePath) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            changeWorkingDirectory(ftpClient, filePath);
            ftpClient.deleteFile(fileName);
        } catch (Exception e) {
            LOGGER.error("文件删除失败, {}", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
    }
    
    /**
     * Description: 目录切换
     *
     * @param ftpClient
     * @param targetPath
     */
    private static void changeWorkingDirectory(FTPClient ftpClient, String targetPath) {
        ftpClient.enterLocalPassiveMode();
        try {
            boolean result = ftpClient.changeWorkingDirectory(targetPath);
            if (!result) {
                ftpClient.changeWorkingDirectory("/");
                String[] paths = targetPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(paths[i]);
                    ftpClient.changeWorkingDirectory(paths[i]);
                }
                if (!ftpClient.changeWorkingDirectory(targetPath)) {
                    LOGGER.error("调转到目标目录失败");
                    return;
                }
            }
        } catch (IOException e) {
            LOGGER.error("调转到目标目录失败, {}", e);
        }
         LOGGER.debug("调转到目标目录");
    }
}
