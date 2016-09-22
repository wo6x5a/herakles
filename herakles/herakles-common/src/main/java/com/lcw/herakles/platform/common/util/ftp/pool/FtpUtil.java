package com.lcw.herakles.platform.common.util.ftp.pool;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.util.ApplicationContextUtil;

/**
 * Class Name: FtpUtil
 * 
 * Description: ftp工具类
 * 
 *
 */
public class FtpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtil.class);

    /**
     * Description: 上传文件
     *
     * @param fileName
     * @param input
     * @param image
     * @param imagePath
     */
    public static void upload(String fileName, InputStream input, String image, String imagePath) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            changeWorkingDirectory(ftpClient, image + imagePath);
            ftpClient.storeFile(fileName, input);
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
    }

    /**
     * Description: 删除文件
     *
     * @param fileName
     * @param image
     * @param imagePath
     */
    public static void delete(String fileName, String image, String imagePath) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            changeWorkingDirectory(ftpClient, image + imagePath);
            ftpClient.deleteFile(fileName);
        } catch (Exception e) {
            LOGGER.error("文件删除失败", e);
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
            LOGGER.error("调转到目标目录失败", e);
        }
        LOGGER.debug("调转到目标目录");
    }
}
