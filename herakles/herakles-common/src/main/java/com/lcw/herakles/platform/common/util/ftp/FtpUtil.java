package com.lcw.herakles.platform.common.util.ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.util.ApplicationContextUtil;
import com.lcw.herakles.platform.common.util.ftp.pool.FtpClientPoolFactory;

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

    /**
     * Description: 上传文件
     *
     * @param fileName 文件名
     * @param input
     * @param filePath ftp相对目录
     */
    public static String upload(String fileName, InputStream input, String filePath) {
        StringBuilder resp = new StringBuilder("");
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            changeWorkingDirectory(ftpClient, filePath);
            ftpClient.storeFile(fileName, input);
        } catch (Exception e) {
            LOGGER.error("文件上传失败", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }

        resp.append(filePath);
//        resp.append(File.separator);
        resp.append(fileName);
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
        resp.append(suffix(fileName).toLowerCase());
        return resp.toString();
    }

    /**
     * 获取文件后缀名
     * 
     * @param fileName
     * @return
     */
    private static String suffix(String fileName) {
        return fileName.substring(fileName.indexOf(".") + 1);
    }
}
