package com.lcw.herakles.platform.common.util.file.ftp.pool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: Ftp连接池工厂
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class FtpClientPoolFactory {

	private GenericObjectPool<FTPClient> pool;

	public FtpClientPoolFactory(FtpClientFactory factory, GenericObjectPoolConfig config) {
		pool = new GenericObjectPool<FTPClient>(factory, config);
	}

	public synchronized FTPClient getConnection() {
		FTPClient ftpClient = null;
		try {
			ftpClient = pool.borrowObject();
			if (ftpClient == null) {
				log.error("获得FTP连接失败");
			} else {
			    log.info("获得FTP连接成功");
			}
		} catch (Exception e) {
		    log.error("获得FTP连接失败:", e);
		}
		return ftpClient;
	}

	public void releaseConnection(FTPClient ftClient) {
		pool.returnObject(ftClient);
	}

}
