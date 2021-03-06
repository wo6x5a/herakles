package com.lcw.herakles.platform.common.util.file.ftp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.util.ApplicationContextUtil;
import com.lcw.herakles.platform.common.util.file.FileUtil;
import com.lcw.herakles.platform.common.util.file.ftp.pool.FtpClientPoolFactory;
import com.lcw.herakles.platform.common.util.file.image.ImageMarkUtil;

import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class Name: FtpUtil
 * 
 * Description: ftp工具类
 * 
 * @author chenwulou
 *
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FtpUtil {

    /**
     * 上传图片或文件
     * 
     * @param fileName
     * @param input
     * @param filePath
     * @return
     */
    public static String uploadFile(String fileName, InputStream input, String filePath) {
        return upload(fileName, input, filePath);
    }

    /**
     * 上传图片并打水印
     * 
     * @param fileName
     * @param input
     * @param filePath
     * @return
     */
    public static String uploadImgWithMark(String fileName, InputStream input, String filePath) {
        // 如果水印,输入流则为水印图片输入流
        BufferedImage buf = ImageMarkUtil.markImageByIcon(input, filePath + fileName, -45);
        if(null != buf){
            input = FileUtil.getImageStream(buf);
        } else{
            log.error("FtpUtil.uploadImgWithMark(), buf为空");
        }
        return upload(fileName, input, filePath);
    }

    /**
     * Description: 上传文件
     *
     * @param fileName 文件名
     * @param input
     * @param filePath ftp相对目录
     */
    private static String upload(String fileName, InputStream input, String filePath) {
        StringBuilder resp = new StringBuilder("");
        resp.append(filePath);
        // resp.append(File.separator);
        resp.append(fileName);
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            changeWorkingDirectory(ftpClient, filePath);
            // input = null;
            ftpClient.storeFile(fileName, input);
        } catch (Exception e) {
            log.error("文件上传失败:", e);
        } finally {
            pool.releaseConnection(ftpClient);
        }
        return resp.toString();
    }

    /**
     * 从FTP服务器下载文件
     * 
     * @param ftpPath 文件路径完整
     * @param localPath 下载到本地文件地址
     * @return
     */
    public static boolean download(String ftpPath, String localPath) {
        File file = new File(ftpPath);
        String remotePath = file.getParent();
        String fileName = file.getName();
        return download(remotePath, fileName, localPath);
    }

    /**
     * 从FTP服务器下载文件
     * 
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载到本地文件地址
     */
    public static boolean download(String remotePath, String fileName, String localPath) {
        FtpClientPoolFactory pool = ApplicationContextUtil.getBean(FtpClientPoolFactory.class);
        FTPClient ftpClient = pool.getConnection();
        try {
            // 转移到FTP服务器上的相对路径
            ftpClient.changeWorkingDirectory(remotePath);
            // 获取文件列表
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (StringUtils.equals(ff.getName(), fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    @Cleanup OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), os);
                }
            }
        } catch (Exception e) {
            log.error("文件下载失败:", e);
            return false;
        } finally {
            pool.releaseConnection(ftpClient);
        }
        return true;
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
            log.error("文件删除失败:", e);
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
            if (!ftpClient.changeWorkingDirectory(targetPath)) {
                // 跳转到ftp根目录
                ftpClient.changeWorkingDirectory(ApplicationConsts.FTP_PARENT_PATH);
                String[] paths = targetPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(paths[i]);
                }
                if (!ftpClient.changeWorkingDirectory(targetPath)) {
                    log.error("调转到目标目录失败");
                    return;
                }
            }
        } catch (IOException e) {
            log.error("调转到目标目录失败:", e);
        }
        log.debug("调转到目标目录");
    }
}
