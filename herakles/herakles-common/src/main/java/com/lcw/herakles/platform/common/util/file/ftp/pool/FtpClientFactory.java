package com.lcw.herakles.platform.common.util.file.ftp.pool;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.lcw.herakles.platform.common.util.file.ftp.dto.FtpInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Description: Ftp连接池工厂
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class FtpClientFactory extends BasePooledObjectFactory<FTPClient> {

	private FtpInfo ftpInfo;

	public void setFtpInfo(FtpInfo ftpInfo) {
		this.ftpInfo = ftpInfo;
	}

	@Override
	public FTPClient create() {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpInfo.getFtpHost(), ftpInfo.getFtpPort());// 连接FTP服务器
			ftpClient.login(ftpInfo.getFtpUserName(), ftpInfo.getFtpPassword());// 登陆FTP服务器
//			log.debug("IP:" + ftpInfo.getFtpHost() + ", 端口：" + ftpInfo.getFtpPort() + "，用户名："
//			        + ftpInfo.getFtpUserName() + "，密码" + ftpInfo.getFtpPassword());
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				log.info("FTP连接成功");
			} else {
				ftpClient.disconnect();
				log.error("未连接到FTP,用户名或密码错误");
			}
		} catch (SocketException e) {
			log.error("FTP的IP地址可能错误,请正确配置:", e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ftpClient;
	}

	@Override
	public PooledObject<FTPClient> wrap(FTPClient obj) {
		return new DefaultPooledObject<FTPClient>(obj);
	}

	@Override
	public void destroyObject(PooledObject<FTPClient> p) {
		try {
			p.getObject().disconnect();
			log.info("FTP连接关闭");
		} catch (IOException e) {
			log.error("FTP关闭连接错误:", e);
		}
	}

	@Override
	public boolean validateObject(PooledObject<FTPClient> p) {
		FTPClient ftpClient = p.getObject();
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			return true;
		}
		return false;
	}

}
