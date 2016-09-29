package com.lcw.herakles.platform.common.util.file.ftp.pool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Ftp连接池工厂
 * 
 * @author chenwulou
 *
 */
public class FtpClientPoolFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(FtpClientPoolFactory.class);

	private GenericObjectPool<FTPClient> pool;

	public FtpClientPoolFactory(FtpClientFactory factory, GenericObjectPoolConfig config) {
		pool = new GenericObjectPool<FTPClient>(factory, config);
	}

	public synchronized FTPClient getConnection() {
		FTPClient ftpClient = null;
		try {
			ftpClient = pool.borrowObject();
			if (ftpClient == null) {
				LOGGER.error("获得FTP连接失败");
			} else {
				LOGGER.info("获得FTP连接成功");
			}
		} catch (Exception e) {
			LOGGER.error("获得FTP连接失败, {}", e);
		}
		return ftpClient;
	}

	public void releaseConnection(FTPClient ftClient) {
		pool.returnObject(ftClient);
	}

}
